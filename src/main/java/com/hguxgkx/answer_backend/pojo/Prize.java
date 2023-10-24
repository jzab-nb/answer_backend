package com.hguxgkx.answer_backend.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prize {
    // 对应的试卷id
    private int id;
    // 有抽奖资格的学生
    private List<String> stu;
    // 奖池(奖品列表)
    private List<String> prz;
    // 综合奖池, (针对可抽奖的人数随机生成一个数组，数组长度为可抽奖人数，数组内容包含奖品和谢谢参与)
    private List<String> totalPrz;
    // 抽奖记录
    private List<Map> log;

    public String getStu(){
        return JSON.toJSONString(stu);
    }

    public List<String> getStuObject(){
        return this.stu;
    }

    public void setStu(String stu){
        this.stu = JSON.parseArray(stu, String.class);
    }

    public String getPrz(){
        return JSON.toJSONString(prz);
    }

    public List<String> getPrzObject(){
        return this.prz;
    }

    public void setPrz(String prz){
        this.prz = JSON.parseArray(prz, String.class);
    }

    public String getTotalPrz(){
        return JSON.toJSONString(totalPrz);
    }

    public List<String> getTotalPrzObject(){
        return this.totalPrz;
    }

    public void setTotalPrz(String totalPrz){
        this.totalPrz = JSON.parseArray(totalPrz, String.class);
    }

    public String getLog() {
        return JSON.toJSONString(log);
    }

    public void setLog(String log) {
        this.log = JSON.parseArray(log, Map.class);
    }

    public List<Map> getLogObject(){
        return this.log;
    }

    public void addLog(Map<String,String> map){
        this.log.add(map);
    }
}
