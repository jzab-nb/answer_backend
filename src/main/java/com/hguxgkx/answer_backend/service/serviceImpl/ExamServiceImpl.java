package com.hguxgkx.answer_backend.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.hguxgkx.answer_backend.common.entity.R;
import com.hguxgkx.answer_backend.mapper.*;
import com.hguxgkx.answer_backend.pojo.ChoiceQuestion;
import com.hguxgkx.answer_backend.pojo.Exam;
import com.hguxgkx.answer_backend.pojo.Paper;
import com.hguxgkx.answer_backend.pojo.TextQuestion;
import com.hguxgkx.answer_backend.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    PaperMapper paperMapper;
    @Autowired
    ChoiceQuestionMapper choiceQuestionMapper;
    @Autowired
    TextQuestionMapper textQuestionMapper;

    @Override
    public R getExamList(String id) {
        return R.OK( examMapper.getExamByStudentId(id) );
    }

    @Override
    public R getQuestion(int questionId, String studentId, int examId) {
        Exam examById = examMapper.getExamById(examId);
        Paper paperById = paperMapper.getPaperById(examById.getPaperId( ));
        if(examById==null){
            return R.Error("试卷不存在");
        }else if(!examById.getStudentId().equals(studentId)){
            return R.Error("试卷不属于你");
        }else{
            //如果考试的题目列表为空就去寻找试卷的题目列表
            List<Object> questionsList = examById.getQuestionsListObject( );
            if(questionsList==null){
                questionsList = paperById.getQuestionsListObject( );
            }
            if(questionId>=questionsList.size( ) || questionId<0){return R.Error( "数组越界" );}
            Map<String,Integer> m = (Map<String, Integer>) questionsList.get(questionId);
            R r = new R();
            r.put("question_number",paperById.getQuestionNumber());
            r.put("type",m.get("type"));
            switch (m.get("type")){
                case 0:
                case 1:
                    ChoiceQuestion choiceQuestion = choiceQuestionMapper.getQuestionById(m.get("id"));
                    r.put("stem",choiceQuestion.getStem());
                    r.put("chose",choiceQuestion.getOptionsObject());
                    break;
                case 2:
                    TextQuestion textQuestion = textQuestionMapper.getQuestionById(m.get("id"));
                    r.put("stem",textQuestion.getStem());
                    break;
                default:return R.Error("错误的题目类型");
            }
            return R.OK(r);
        }
    }

    @Override
    public R submitAnswer(List<Object> list, String studentId, int examId) {
        Exam examById = examMapper.getExamById(examId);
        if(examById==null){
            return R.Error("试卷不存在");
        }else if(!examById.getStudentId().equals(studentId)) {
            return R.Error("试卷不属于你");
        } else if(examById.getEndExam()==1){
            return R.Error( "考试已经结束,不许重复提交" );
        }else {
            //如果考试的题目列表为空就去寻找试卷的题目列表
            List<Object> questionsList = examById.getQuestionsListObject( );
            List<Integer> scoreList = examById.getCustomScoreObject();
            if (questionsList == null) {
                Paper paperById = paperMapper.getPaperById(examById.getPaperId( ));
                questionsList = paperById.getQuestionsListObject( );
                scoreList = paperById.getCustomScoreObject();
            }
            if (list.size() != questionsList.size( )) {
                return R.Error("问题答案列表与问题列表不等长");
            }
            //遍历题目列表
            int score=0;
            List<Integer> trueFalseList = new ArrayList<Integer>(  );
            for(int i=0;i<questionsList.size();i++){
                Map<String, Integer> map = (Map<String, Integer>) questionsList.get(i);
                if(map.get("type")==0 ){
                    //计算单项选择的分数
                    //如果答案正确
                    if(list.get(i)==choiceQuestionMapper.getQuestionById(map.get("id")).getAnswerObject().get(0)){
                        //分数加
                        trueFalseList.add(i,1);
                        score+=scoreList.get(i);
                    }else {
                        trueFalseList.add(i,0);
                    }
                }else if(map.get("type")==1){
                    //计算多项选择的分数
                    List<Integer> answerList = null;
                    try {
                        answerList = (List<Integer>)list.get(i);
                    } catch (Exception e) {
                        return R.Error( i+1+"题是多选题却传来的是单选答案" );
                    }
                    if(list.get(i)==null){
                        return R.Error( i+1+"题未答" );
                    }
                    //分数加
                    trueFalseList.add(i,1);
                    score+=scoreList.get(i);
                    if(choiceQuestionMapper.getQuestionById(map.get("id")).getAnswerObject().size()==answerList.size()){
                        for(int j=0;j<choiceQuestionMapper.getQuestionById(map.get("id")).getAnswerObject().size();j++){
                            if(answerList.get(j)!=choiceQuestionMapper.getQuestionById(map.get("id")).getAnswerObject().get(j)){
                                //碰到不正确的分数减
                                trueFalseList.set(i,0);
                                score-=scoreList.get(i);
                                break;
                            }
                        }
                    }else{
                        trueFalseList.set(i,0);
                        score-=scoreList.get(i);
                    }
                }else{
                    trueFalseList.add(i,0);
                }
            }
            R r = new R();
            r.put("id",examId);
            r.put("endExam",1);
            r.put("score",score);
            r.put("answersList",JSON.toJSONString(list));
            r.put("trueFalseList",JSON.toJSONString(trueFalseList));
            //将答案列表更新到数据库
            examMapper.uploadAnswers(r);
            //更新分数,更新答完
            examMapper.changeEndExam(r);
            examMapper.changeScore(r);
            examMapper.changeTrueFalseList(r);
            System.out.println(r );
            return R.<Integer>OK(score);
        }
    }

    @Override
    public R getStudentList(int paperId) {
        List<Map<String, Object>> examByPaperId = examMapper.getExamByPaperId(paperId);
        for (Map<String, Object> map : examByPaperId) {
            System.out.println(map );
            System.out.println(map.get("student_id") );
            System.out.println(userMapper.getUserById((String) map.get("student_id")) );
            map.put("name",userMapper.getUserById((String) map.get("student_id")).getName());
        }
        return R.OK(examByPaperId);
    }

    @Override
    public R getExamInfo(int examId) {
        Exam examById = examMapper.getExamById(examId);
        if(examById == null) return R.Error( "考试不存在" );
        R r = new R();
        r.put("name",userMapper.getUserById(examById.getStudentId()).getName());
        r.put("student_id",examById.getStudentId());
        r.put("score",examById.getScore());
        r.put("end_exam",examById.getEndExam());
        r.put("true_false_list",examById.getTrueFalseListObject());
        //查询题目列表
        List<String> question_list = new ArrayList<>(  );
        List<String> answer_list = new ArrayList<>(  );
        int i;
        List<Object> questionsListObject = examById.getQuestionsListObject( );
        if(questionsListObject == null){
            //如果考试没有试题列表就去找试卷的
            questionsListObject = paperMapper.getPaperById(examById.getPaperId()).getQuestionsListObject();
        }
        List<Object> answersListObject = examById.getAnswersListObject( );
        if(answersListObject != null){
            for (i=0;i<questionsListObject.size();i++) {
                Map<String, Integer> map = (Map<String,Integer>)questionsListObject.get(i);
                if(map.get("type")==2){
                    //题干
                    question_list.add(textQuestionMapper.getQuestionById(map.get("id")).getStem());
                    answer_list.add((String) answersListObject.get(i));
                }
            }
        }
        r.put("answer_list",answer_list);
        r.put("question_list",question_list);
        return R.OK(r);
    }
}
