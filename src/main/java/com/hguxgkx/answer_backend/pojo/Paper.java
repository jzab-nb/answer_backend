package com.hguxgkx.answer_backend.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/*
* 试卷的实体类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
    private int id;
    private String authorId;
    private Date openTime;
    private Date endTime;
    private int questionNumber;
    private int random;
    private int publish;
    private int isDelete;
    private List<Integer> face;
    private List<Object> questionsList;
    private List<Integer> customScore;
    private String paperName;

    public void setQuestionsList(String questionsList) {
        this.questionsList = JSON.parseArray(questionsList );
    }

    public void setCustomScore(String customScore) {
        this.customScore = JSON.parseArray(customScore,Integer.class );
    }

    public void setFace(String face) {
        this.face = JSON.parseArray(face,Integer.class);
    }

    public String getFace() {
        if(this.face == null) return null;
        return JSON.toJSONString(this.face);
    }

    public String getQuestionsList() {
        if(this.questionsList == null) return null;
        return JSON.toJSONString(this.questionsList);
    }

    public String getCustomScore() {
        if(this.customScore == null) return null;
        return JSON.toJSONString(this.customScore);
    }

    public List<Integer> getFaceObject() {
        return this.face;
    }

    public List<Object> getQuestionsListObject() {
        return this.questionsList;
    }

    public List<Integer> getCustomScoreObject() {
        return this.customScore;
    }

    public void addQuestion(int type,int id,int score){
        if(this.questionsList==null) this.questionsList = new ArrayList<>(  );
        if(this.customScore==null) this.customScore = new ArrayList<>(  );
        Map<String,Integer> map = new HashMap<>(  );
        map.put("type",type);
        map.put("id",id);
        this.questionsList.add(map);
        this.customScore.add(score);
        this.questionNumber++;
    }
}
