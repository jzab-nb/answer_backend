package com.hguxgkx.answer_backend.service.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.hguxgkx.answer_backend.common.entity.R;
import com.hguxgkx.answer_backend.common.entity.UserExcelModel;
import com.hguxgkx.answer_backend.config.JwtConfigPropreties;
import com.hguxgkx.answer_backend.mapper.ExamMapper;
import com.hguxgkx.answer_backend.mapper.PaperMapper;
import com.hguxgkx.answer_backend.mapper.UserMapper;
import com.hguxgkx.answer_backend.pojo.Exam;
import com.hguxgkx.answer_backend.pojo.Paper;
import com.hguxgkx.answer_backend.pojo.User;
import com.hguxgkx.answer_backend.service.UserService;
import com.hguxgkx.answer_backend.utils.JwtUtils;
import com.hguxgkx.answer_backend.utils.ModelExcelListener;
import com.hguxgkx.answer_backend.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PaperMapper paperMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    JwtConfigPropreties jwtConfigPropreties;

    @Override
    public R login(String id, String name) {
        User userById = userMapper.getUserById(id);
        if(userById != null && userById.getName().equals(name)){
            String str = JwtUtils.createToken(id,
                    jwtConfigPropreties.getExp( ),
                    jwtConfigPropreties.getSignature());
            R r = new R();
            r.put("token",str);
            r.put("roles",userById.getRole());
            return R.OK(r);
        } else{
            return R.Error("登录失败");
        }
    }

    @Override
    public R register(String name) {
        //temp00000001开始
        String maxId = userMapper.getMaxId( );//获取上一个id
        int i;
        System.out.println(maxId );
        System.out.println(maxId.substring(0,4) );
        System.out.println(maxId.substring(0,4).equals("temp") );
        if(!maxId.substring(0,4).equals("temp")){
            System.out.println("上" );
            i = 0;
        }else{
            System.out.println("下" );
            i = Integer.parseInt(maxId.substring(4));//截取出数字部分
        }
        User user = new User(String.format("temp%08d", i+1),name,"",-1,null );//id+1创建新的用户
        userMapper.addUser(user);
        //获取所有面向访客的试卷
        List<Paper> paperByFace = paperMapper.getPaperByFace("[-1]");
        //生成考试
        for (Paper paper : paperByFace) {
            Exam exam = new Exam( user.getId(),paper.getId(),null,null );
            if(paper.getRandom()==1){
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
        String str = JwtUtils.createToken(user.getId(),
                jwtConfigPropreties.getExp( ),
                jwtConfigPropreties.getSignature());
        R r = new R();
        r.put("token",str);
        r.put("roles",user.getRole());
        return R.OK(r);
    }

    @Override
    public R input() {
        try {
            InputStream stream = new FileInputStream("学生.xlsx");
            List<UserExcelModel> userList;
            List<User> users = new ArrayList<>(  );
            userList = EasyExcel.read(
                    stream,
                    UserExcelModel.class,
                    new ModelExcelListener()
            ).sheet( 0 ).doReadSync();
            for (UserExcelModel userExcelModel : userList) {
                User user = new User(userExcelModel.getStuId(), userExcelModel.getName(), userExcelModel.getClasses(),1, null);
                userMapper.addUser(user);
                users.add(user);
            }
            return R.OK(users);
        } catch (FileNotFoundException e) {
            e.printStackTrace( );
        }
        return R.Error(  );
    }
}
