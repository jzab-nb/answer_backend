package com.hguxgkx.answer_backend;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.hguxgkx.answer_backend.common.entity.MultipleChoiceExcelModel;
import com.hguxgkx.answer_backend.common.entity.TextQuestionExcelModel;
import com.hguxgkx.answer_backend.mapper.*;
import com.hguxgkx.answer_backend.pojo.*;
import com.hguxgkx.answer_backend.service.PrizeService;
import com.hguxgkx.answer_backend.service.serviceImpl.PrizeServiceImpl;
import com.hguxgkx.answer_backend.service.serviceImpl.UserServiceImpl;
import com.hguxgkx.answer_backend.utils.FileUtils;
import com.hguxgkx.answer_backend.utils.ModelExcelListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

//@SpringBootTest
//class MapperTests {
//    @Autowired
//    UserMapper userMapper;
//    @Autowired
//    RolesMapper rolesMapper;
//    @Autowired
//    ExamMapper examMapper;
//    @Autowired
//    PaperMapper paperMapper;
//    @Autowired
//    ChoiceQuestionMapper choiceQuestionMapper;
//    @Autowired
//    TextQuestionMapper textQuestionMapper;
//
//    @Test
//    void userMapperTest() {
//        List<Integer> list = new ArrayList<Integer>(  );
//        list.add(1);
//        list.add(2);
//        Map map = new HashMap(  );
//        map.put("exams", JSON.toJSONString(list));
//        map.put("id", "420109070116");
//        System.out.println(userMapper.updateExamList(map));
//        System.out.println(userMapper.getUserById("420109070116") );
//        System.out.println(userMapper.getExamList("420109070116").get("exams") );
//    }
//
//    @Test
//    void rolesMapperTest(){
//        System.out.println(rolesMapper.getRolesList( ));
//        System.out.println(rolesMapper.getRolesByID(1));
//    }
//
//    @Test
//    void examMapperTest(){
////        List<Object> questionList = new ArrayList<>(  );
////        questionList.add(1);
////        questionList.add(2);
////        questionList.add(3);
////
////        List<Integer> customScore = new ArrayList<>(  );
////        customScore.add(1);
////        customScore.add(2);
////        customScore.add(3);
////        Exam exam = new Exam(
////                0,"420109070116",1,0,0,0,
////                null,null,null);
////        System.out.println(exam );
////        examMapper.addExam(exam);
//
////        examMapper.deleteExam(1);
////        examMapper.deleteExamByPaperId(1);
//
////        List<Object> list = new ArrayList<Object>(  );
////        list.add(1);
////        list.add("2");
////        list.add(444);
////        Map<String,Object> map1 = new HashMap<String,Object>(  );
////        Map<String,Object> map2 = new HashMap<String,Object>(  );
////        Map<String,Object> map3 = new HashMap<String,Object>(  );
////        Map<String,Object> map4 = new HashMap<String,Object>(  );
////        map1.put("id",1);
////        map2.put("id",2);
////        map3.put("id",3);
////        map4.put("id",4);
////        map1.put("answersList",JSON.toJSONString(list));
////        map2.put("endExam",1);
////        map3.put("endJudge",1);
////        map4.put("score",100);
////        examMapper.uploadAnswers(map1);
////        examMapper.changeEndExam(map2);
////        examMapper.changeEndJudge(map3);
////        examMapper.changeScore(map4);
//
////        System.out.println(examMapper.getExamById(4));
////        System.out.println(examMapper.getExamByPaperId(1));
//
////        System.out.println(examMapper.getExamByStudentId("420109070116"));
////
////        System.out.println(examMapper.countEndExam(1) );
////        System.out.println(examMapper.countAll(1) );
//
//        examMapper.getExamByPaperId(1);
//    }
//
//    @Test
//    void paperMapperTest(){
////        Paper paper = new Paper(
////                0,"420109070116",
////                new Date(  ),new Date(  ),
////                0,1,0,
////                JSON.parseArray("[0,1,2]",Integer.class),null,null);
////        paperMapper.addPaper(paper);
//
////        paperMapper.deletePaper(1);
//
////        Map<String,Object> map = new HashMap<String,Object>( );
////        map.put("openTime",new Date(  ));
////        map.put("id",3);
////        paperMapper.publishPaper(1);
////        paperMapper.changeQuestionsList(JSON.parseObject("{questionsList:'[1,2,3]',id:2}"));
////        paperMapper.changeOpenTime(map);
////        map.put("id",4);
////        map.put("endTime",new Date(  ));
////        paperMapper.changeEndTime(map);
////        paperMapper.changeRandom(JSON.parseObject("{random:123,id:5}"));
////        paperMapper.changeCustomScore(JSON.parseObject("{customScore:'[1,2,3,114514]',id:6}"));
//        System.out.println(paperMapper.getPaperById(6) );
//        System.out.println(paperMapper.getPaperByAuthorId("420109070116") );
//    }
//
//    @Test
//    void choiceQuestionMapperTest(){
////        ChoiceQuestion question = new ChoiceQuestion(
////                0,"420109070126","题干",0,
////                JSON.parseArray("['011','022']",String.class),JSON.parseArray("[1,3]",Integer.class),
////                12,0);
////        choiceQuestionMapper.addQuestion(question);
////        choiceQuestionMapper.deleteQuestion(2);
//
////        System.out.println(choiceQuestionMapper.getQuestionById(3));
////        System.out.println(choiceQuestionMapper.getQuestionByAuthorId("420109070116"));
//
//        System.out.println(choiceQuestionMapper.getMaxId() );
//    }
//
//    @Test
//    void TextQuestionMapperTest(){
////        TextQuestion question = new TextQuestion(
////                0,"420109070126","题干",
////                "答案啊啊啊",
////                12,0);
////        textQuestionMapper.addQuestion(question);
////        textQuestionMapper.deleteQuestion(2);
//
////        System.out.println(textQuestionMapper.getQuestionById(3));
////        System.out.println(textQuestionMapper.getQuestionByAuthorId("420109070116"));
//
//        System.out.println(textQuestionMapper.getMaxId() );
//    }
//}

@SpringBootTest
class SmallTest{
    @Autowired
    UserServiceImpl userService;

    @Autowired
    ChoiceQuestionMapper choiceQuestionMapper;

    @Autowired
    PrizeServiceImpl prizeService;

    @Test
    void ExcelTest(){
//        List<String> list = new ArrayList<>(  );
//        list.add("钥匙扣");
//        list.add("台灯");
//        list.add("刘李翰");
//        System.out.println(prizeService.init(1, list));
//        System.out.println(prizeService.extract(1, "420109070210"));
//        try{
//            System.out.println(choiceQuestionMapper.getMaxId( ));
//        }catch (Exception e){
//            System.out.println(e.getMessage() );
//        }
//        System.out.println("hello" );
//        System.out.println(userService.input( ));
    }
}
