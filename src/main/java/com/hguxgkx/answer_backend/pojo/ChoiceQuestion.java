package com.hguxgkx.answer_backend.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 选择题的实体类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceQuestion {
    private int id;
    private String authorId;
    private String stem;
    private int multiple;
    private List<String> options;
    private List<Integer> answer;
    private int score;
    private int isDelete;

    public void setOptions(String options) {
        this.options = JSON.parseArray(options,String.class );
    }

    public void setAnswer(String answer) {
        this.answer = JSON.parseArray(answer,Integer.class);
    }

    public String getOptions() {
        if(this.options == null) return null;
        return JSON.toJSONString(this.options);
    }

    public String getAnswer() {
        if(this.answer == null) return null;
        return JSON.toJSONString(this.answer);
    }

    public List<String> getOptionsObject() {
        return this.options;
    }

    public List<Integer> getAnswerObject() {
        return this.answer;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>(  );
        map.put("stem",this.stem);
        map.put("options",this.options);
        map.put("answer",this.answer);
        map.put("score",this.score);
        if(this.multiple==0){
            map.put("type",0);
        }else{
            map.put("type",1);
        }
        return map;
    }
}
