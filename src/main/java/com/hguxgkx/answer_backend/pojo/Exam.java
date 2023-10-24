package com.hguxgkx.answer_backend.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
* 考试表的实体类,用于处理用户和试卷的多对多关系
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    private int id;
    private String studentId;
    private int paperId;
    private int score;
    private int endExam;
    private int endJudge;
    private  List<Integer> trueFalseList;
    private List<Object> questionsList;
    private List<Object> answersList;
    private List<Integer> customScore;

    public Exam(String studentId, int paperId, List<Object> questionsList, List<Integer> customScore) {
        this.studentId = studentId;
        this.paperId = paperId;
        this.questionsList = questionsList;
        this.customScore = customScore;
    }

    public void setQuestionsList(List<Object> questionsList) {
        this.questionsList = questionsList;
    }

    public void setCustomScore(List<Integer> customScore) {
        this.customScore = customScore;
    }

    public void setQuestionsList(String questionsList) {
        this.questionsList = JSON.parseArray(questionsList );
    }

    public void setAnswersList(String answersList) {
        this.answersList = JSON.parseArray(answersList );
    }

    public void setCustomScore(String customScore) {
        this.customScore = JSON.parseArray(customScore,Integer.class );
    }

    public void setTrueFalseList(String trueFalseList) {
        this.trueFalseList = JSON.parseArray(trueFalseList,Integer.class);
    }


    public String getQuestionsList() {
        if(this.questionsList == null) return null;
        return JSON.toJSONString(this.questionsList);
    }

    public String getAnswersList() {
        if(this.answersList == null) return null;
        return JSON.toJSONString(this.answersList);
    }

    public String getCustomScore() {
        if(this.customScore == null) return null;
        return JSON.toJSONString(this.customScore);
    }

    public String getTrueFalseList() {
        if(this.trueFalseList == null) return null;
        return JSON.toJSONString(this.trueFalseList);
    }

    public List<Object> getQuestionsListObject() {
        return this.questionsList;
    }

    public List<Object> getAnswersListObject() {
        return this.answersList;
    }

    public List<Integer> getCustomScoreObject() {
        return this.customScore;
    }

    public List<Integer> getTrueFalseListObject() {
        return this.trueFalseList;
    }
}
