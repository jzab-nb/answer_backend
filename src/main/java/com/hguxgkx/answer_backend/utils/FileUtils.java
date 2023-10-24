package com.hguxgkx.answer_backend.utils;

import com.alibaba.fastjson.JSON;
import com.hguxgkx.answer_backend.common.entity.MultipleChoiceExcelModel;
import com.hguxgkx.answer_backend.common.entity.SingleChoiceExcelModel;

import java.util.*;

/*
* 处理excel里读出的数据的工具类
* */
public class FileUtils {
    public static String optionsConnect(SingleChoiceExcelModel model){
        List<String> list = new ArrayList<>(  );
        do {
            if (model.getOptionA( ) == null) break;
            list.add(model.getOptionA( ));
            if (model.getOptionB( ) == null) break;
            list.add(model.getOptionB( ));
            if (model.getOptionC( ) == null) break;
            list.add(model.getOptionC( ));
            if (model.getOptionD( ) == null) break;
            list.add(model.getOptionD( ));
            if (model.getOptionE( ) == null) break;
            list.add(model.getOptionE( ));
            if (model.getOptionF( ) == null) break;
            list.add(model.getOptionF( ));
            if (model.getOptionG( ) == null) break;
            list.add(model.getOptionG( ));
            break;
        }while (true);
        if(list.isEmpty()) return null;
        return JSON.toJSONString(list);
    }

    public static String optionsConnect(MultipleChoiceExcelModel model){
        List<String> list = new ArrayList<>(  );
        do {
            if (model.getOptionA( ) == null) break;
            list.add(model.getOptionA( ));
            if (model.getOptionB( ) == null) break;
            list.add(model.getOptionB( ));
            if (model.getOptionC( ) == null) break;
            list.add(model.getOptionC( ));
            if (model.getOptionD( ) == null) break;
            list.add(model.getOptionD( ));
            if (model.getOptionE( ) == null) break;
            list.add(model.getOptionE( ));
            if (model.getOptionF( ) == null) break;
            list.add(model.getOptionF( ));
            if (model.getOptionG( ) == null) break;
            list.add(model.getOptionG( ));
            break;
        }while (true);
        if(list.isEmpty()) return null;
        return JSON.toJSONString(list);
    }

    public static String answerConnect(SingleChoiceExcelModel model){
        List<Integer> list = new ArrayList<>(  );
        switch (model.getAnswer()){
            case "A":
            case "a":list.add(0);break;
            case "B":
            case "b":list.add(1);break;
            case "C":
            case "c":list.add(2);break;
            case "D":
            case "d":list.add(3);break;
            case "E":
            case "e":list.add(4);break;
            case "F":
            case "f":list.add(5);break;
            case "G":
            case "g":list.add(6);break;
        }
        if(list.size( ) != 1) return null;
        return JSON.toJSONString(list);
    }

    public static String answerConnect(MultipleChoiceExcelModel model){
        List<String> list = Arrays.asList(model.getAnswer( ).split("\\."));
        Set<Integer> rList = new TreeSet<>(  );
        for (String s : list) {
            switch (s){
                case "A":
                case "a":rList.add(0);break;
                case "B":
                case "b":rList.add(1);break;
                case "C":
                case "c":rList.add(2);break;
                case "D":
                case "d":rList.add(3);break;
                case "E":
                case "e":rList.add(4);break;
                case "F":
                case "f":rList.add(5);break;
                case "G":
                case "g":rList.add(6);break;
            }
        }
        if(rList.size()<=1) return null;
        return JSON.toJSONString(rList);
    }
}
