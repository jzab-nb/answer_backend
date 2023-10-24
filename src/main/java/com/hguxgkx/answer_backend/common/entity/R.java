package com.hguxgkx.answer_backend.common.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class R extends HashMap<String,Object> {
    public static R OK(){
        R r = new R();
        r.put("code",0);
        r.put("msg","成功");
        return r;
    }

    public static <T> R OK(T e){
        R r = new R();
        r.put("code",0);
        r.put("msg","成功");
        r.put("result",e);
        return r;
    }

    public static R OK(Map<String,Object> map){
        R r = new R();
        r.put("code",0);
        r.put("msg","成功");
        r.put("result",map);
        return r;
    }

    public static R OK(List<Map<String,Object>> list){
        R r = new R();
        r.put("code",0);
        r.put("msg","成功");
        r.put("result",list);
        return r;
    }

    public static R OK(String msg){
        R r = new R();
        r.put("code",0);
        r.put("msg",msg);
        return r;
    }

    public static R TokenError(){
        R r = new R();
        r.put("code",1);
        r.put("msg","无token或token解析失败");
        return r;
    }

    public static R Error(){
        R r = new R();
        r.put("code",2);
        r.put("msg","其他错误");
        return r;
    }

    public static R Error(String msg){
        R r = new R();
        r.put("code",2);
        r.put("msg",msg);
        return r;
    }
}
