package com.hguxgkx.answer_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/*
 * 简答题的实体类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextQuestion {
    private int id;
    private String authorId;
    private String stem;
    private String answer;
    private int score;
    private int isDelete;

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>(  );
        map.put("stem",this.stem);
        map.put("answer",this.answer);
        map.put("score",this.score);
        map.put("type",2);

        return map;
    }
}
