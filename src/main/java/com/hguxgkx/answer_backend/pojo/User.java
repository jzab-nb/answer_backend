package com.hguxgkx.answer_backend.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * 用户的实体类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String classes;
    private int role;
    private List<Integer> exams;

    public void setExams(String exams) {
        this.exams = JSON.parseArray(exams,Integer.class );
    }

    public String getExams() {
        return JSON.toJSONString(this.exams);
    }
}
