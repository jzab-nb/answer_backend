package com.hguxgkx.answer_backend.service.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.hguxgkx.answer_backend.common.entity.*;
import com.hguxgkx.answer_backend.mapper.*;
import com.hguxgkx.answer_backend.pojo.*;
import com.hguxgkx.answer_backend.service.PaperService;
import com.hguxgkx.answer_backend.utils.FileUtils;
import com.hguxgkx.answer_backend.utils.ModelExcelListener;
import com.hguxgkx.answer_backend.utils.RandomUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RolesMapper rolesMapper;
    @Autowired
    PaperMapper paperMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    ChoiceQuestionMapper choiceQuestionMapper;
    @Autowired
    TextQuestionMapper textQuestionMapper;

    @Override
    public R uploadTemplate(MultipartFile file,String id) throws IOException {
//        存放从excel中直接读取出来的数据
        List<SingleChoiceExcelModel> singleChoiceList;
        List<MultipleChoiceExcelModel> multipleChoiceList;
        List<TextQuestionExcelModel> textQuestionList;
        singleChoiceList = EasyExcel.read(
                file.getInputStream(),
                SingleChoiceExcelModel.class,
                new ModelExcelListener()
        ).sheet(1).doReadSync();
        multipleChoiceList = EasyExcel.read(
                file.getInputStream(),
                MultipleChoiceExcelModel.class,
                new ModelExcelListener()
        ).sheet(2).doReadSync();
        textQuestionList = EasyExcel.read(
                file.getInputStream(),
                TextQuestionExcelModel.class,
                new ModelExcelListener()
        ).sheet(3).doReadSync();
//        生成试卷对象（问题列表，问题数，分数列表需要再赋值）
        int paperId = 1;
        int choiceId = 1;
        int textId = 1;
        //        寻找不同题型的自增id
        try{
            paperId = paperMapper.getMaxId()+1;
        }catch (Exception e){
            System.out.println(e.getMessage() );
        }
        try {
            choiceId = choiceQuestionMapper.getMaxId()+1;
        }catch (Exception e){
            System.out.println(e.getMessage() );
        }
        try{
            textId = textQuestionMapper.getMaxId()+1;
        }catch (Exception e){
            System.out.println(e.getMessage() );
        }
        Paper paper = new Paper(
                paperId,id, new Date(  ),new Date(System.currentTimeMillis()+604800*1000  ),
                0,0,0,0,
                null,null,null,"试卷"+paperId
        );
        if(singleChoiceList.isEmpty()&&multipleChoiceList.isEmpty()&&textQuestionList.isEmpty()) return R.Error( "文件为空" );
//        返回的试题列表
        List<Map<String,Object>> questionList = new ArrayList<>(  );
//        提取出单选题
        List<ChoiceQuestion> choiceQuestionList = new ArrayList<>(  );
        for (SingleChoiceExcelModel singleChoiceExcelModel : singleChoiceList) {
            if(singleChoiceExcelModel.getScore() == null){
                return R.Error( "有的单选题分数未填写" );
            }
            ChoiceQuestion choiceQuestion = new ChoiceQuestion(
                    choiceId++,id,singleChoiceExcelModel.getStem(),0,
                    null,null,
                    singleChoiceExcelModel.getScore(),0
            );
            String s0 = FileUtils.optionsConnect(singleChoiceExcelModel);
            if(s0==null) return R.Error( "传入的excel文件中单选题有没写选项的情况" );
            choiceQuestion.setOptions(s0);
            String s1 = FileUtils.answerConnect(singleChoiceExcelModel);
            if(s1==null) return R.Error( "传入的excel文件中单选题的答案有错误" );
            choiceQuestion.setAnswer(s1);
            choiceQuestionMapper.addQuestion(choiceQuestion);
            paper.addQuestion(0,choiceQuestion.getId(),choiceQuestion.getScore());
            questionList.add(choiceQuestion.toMap());
        }
//        提取出多选题
        for (MultipleChoiceExcelModel multipleChoiceExcelModel : multipleChoiceList) {
            if(multipleChoiceExcelModel.getScore() == null){
                return R.Error( "有的多选题分数未填写" );
            }
            ChoiceQuestion choiceQuestion = new ChoiceQuestion(
                    choiceId++,id,multipleChoiceExcelModel.getStem(),1,
                    null,null,
                    multipleChoiceExcelModel.getScore(),0
            );
            String s0 = FileUtils.optionsConnect(multipleChoiceExcelModel);
            if(s0==null) return R.Error( "传入的excel文件中多选题有没写选项的情况" );
            choiceQuestion.setOptions(s0);
            String s1 = FileUtils.answerConnect(multipleChoiceExcelModel);
            if(s1==null) return R.Error( "传入的excel文件中多题的答案有错误" );
            choiceQuestion.setAnswer(s1);
            choiceQuestionMapper.addQuestion(choiceQuestion);
            paper.addQuestion(1,choiceQuestion.getId(),choiceQuestion.getScore());
            questionList.add(choiceQuestion.toMap());
        }
//        提取出简答题
        for (TextQuestionExcelModel textQuestionExcelModel : textQuestionList) {
            if(textQuestionExcelModel.getScore() == null){
                return R.Error( "有的简答题分数未填写" );
            }
            TextQuestion textQuestion = new TextQuestion(
                textId++,id,
                textQuestionExcelModel.getStem(),textQuestionExcelModel.getAnswer(), textQuestionExcelModel.getScore(),
                0
            );
            textQuestionMapper.addQuestion(textQuestion);
            paper.addQuestion(2,textQuestion.getId(),textQuestion.getScore());
            questionList.add(textQuestion.toMap());
        }
        paperMapper.addPaper(paper);
//        返回试卷id和题目列表
        R r = new R();
        r.put("paper_id",paper.getId());
        r.put("question_list",questionList);
        return R.OK(r);
    }

    @Override
    public R publicPaper(String author_id, Map<String, Object> map) {
        Paper paper = paperMapper.getPaperById((Integer) map.get("paper_id"));
        if(paper.getPublish()==1) return R.Error( "试卷已经发布,请勿重复发布" );
        if(!paper.getAuthorId( ).equals(author_id)) return R.Error( "试卷不属于你" );
        List<String> type = (List<String>)map.get("type");
        List<Integer> face = new ArrayList<>(  );
        for (String s : type) {
            face.add(rolesMapper.getRolesByName(s).getId( ));
        }
//        更新paper数据库
        Map<String,Object> m = new HashMap<>(  );
        m.put("id",map.get("paper_id"));
        m.put("paper_name",map.get("paper_name"));
        m.put("random",(Boolean)map.get("random")?1:0);
        m.put("face", JSON.toJSONString(face));
        paperMapper.publishPaper(m);
//        更新exam数据库:找到所有面向的学生,给这些学生生成考试,如果随机的话生成对应的试题列表与分数列表
        for (Integer  rolesId: face) {
            List<User> userByRoles = userMapper.getUserByRoles(rolesId);
            //获取属于某个角色的所有用户,为其创建考试
            for (User userByRole : userByRoles) {
                Exam exam = new Exam( userByRole.getId(),(Integer) map.get("paper_id"),null,null );
                //判断是否随机,随机的话生成随机的题目列表
                if((Boolean) map.get("random")==true){
                    List<Integer> customScore = new ArrayList<>(  );
                    List<Object> questionList = new ArrayList<>(  );
                    //将题目列表按题型分开
                    List<List<Map<String, Integer>>> lists = RandomUtils.splitQuestionList(paper.getQuestionsListObject( ));
                    //遍历每个题型
                    for (List<Map<String, Integer>> list : lists) {
                        List<Integer> randomList = RandomUtils.getRandomList(list.size( ));
                        for (Integer randomI : randomList) {
                            questionList.add(list.get(randomI));
                            //将分数加进新列表
                            customScore.add(
                                    paper.getCustomScoreObject().get(//从原分数列表中根据id获取
                                            paper.getQuestionsListObject().lastIndexOf(list.get(randomI))//定位题目出现的位置
                                    )
                            );
                        }
                    }
                    exam.setQuestionsList(questionList);
                    exam.setCustomScore(customScore);
                }
                examMapper.addExam(exam);
            }
        }
        return R.OK();
    }

    @Override
    public R getPaperInfo(int id) {
        Paper paperById = paperMapper.getPaperById(id);
        List<Object> questionsList = paperById.getQuestionsListObject( );
        List<Map<String,Object>> list = new ArrayList<>(  );

        for (Object o : questionsList) {
            Map<String,Integer> map = (Map<String, Integer>) o;
            switch (map.get("type")){
                case 0:
                case 1:
                    ChoiceQuestion choiceQuestion = choiceQuestionMapper.getQuestionById(map.get("id"));
                    list.add(choiceQuestion.toMap());
                    break;
                case 2:
                    TextQuestion textQuestion = textQuestionMapper.getQuestionById(map.get("id"));
                    list.add(textQuestion.toMap());
                    break;
                default:return R.Error("错误的题目类型");
            }
        }
        R r = new R();
        r.put("paper_id",id);
        r.put("question_list",list);
        return R.OK(r);
    }

    @Override
    public R getPaperList(String id) {
        List<Map<String, Object>> paperByAuthorId = paperMapper.getPaperByAuthorId(id);
        for (Map<String, Object> map : paperByAuthorId) {
            map.put("end_exam",examMapper.countEndExam((Integer) map.get("id")));
            map.put("all_exam",examMapper.countAll((Integer) map.get("id")));
        }
        return R.OK(paperByAuthorId);
    }

    @Override
    public R endExam(int paperId) {
        if(paperMapper.endExam(paperId)>0){
            return R.OK();
        }else{
            return R.Error(  );
        }
    }

    @Override
    public XSSFWorkbook outData(int paperId) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        String []columnNames = {"姓名","学院","学号","成绩","班级"};

        Sheet sheet = workbook.createSheet();
        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        titleStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(0);

        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(titleStyle);
        }
        //模拟构造数据
        List<OutPutExcelModel> dataList = new ArrayList<>();
        //这里添加数据
        List<Map<String, Object>> examByPaperId = examMapper.getExamByPaperId(paperId);
        for (Map<String, Object> map : examByPaperId) {
            User user = userMapper.getUserById((String) map.get("student_id"));
            Roles roles = rolesMapper.getRolesByID(user.getRole());
            dataList.add(
                    new OutPutExcelModel(
                            user.getName(),
                            roles.getName(),
                            (String) map.get("student_id"),
                            (int)map.get("score"),
                            user.getClasses()
                    )
            );
        }

        //创建数据行并写入值
        for (int j = 0; j < dataList.size(); j++) {
            OutPutExcelModel outPutExcelModel = dataList.get(j);
            int lastRowNum = sheet.getLastRowNum();
            Row dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(outPutExcelModel.getName());
            dataRow.createCell(1).setCellValue(outPutExcelModel.getRoles());
            dataRow.createCell(2).setCellValue(outPutExcelModel.getId());
            dataRow.createCell(3).setCellValue(outPutExcelModel.getScore());
            dataRow.createCell(4).setCellValue(outPutExcelModel.getClasses());
        }
        return workbook;
    }
}
