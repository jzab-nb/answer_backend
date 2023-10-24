package com.hguxgkx.answer_backend.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//生成指定大小的随机串
    public class RandomUtils {
        public static List<Integer> getRandomList(int max){
            List<Integer> list = new ArrayList<>(  );
            int i;
            for(i=0;i<max;i++){
                list.add(i);
            }

            Collections.shuffle(list);
            return list;
        }

        public static List<List<Map<String,Integer>>> splitQuestionList(List<Object> questionList){
            List<List<Map<String,Integer>>> list= new ArrayList<>(  );
            //单选,多选,简答分开
            list.add(new ArrayList<Map<String,Integer>>(  ));
            list.add(new ArrayList<Map<String,Integer>>(  ));
            list.add(new ArrayList<Map<String,Integer>>(  ));
            for (Object stringIntegerMap : questionList) {
                Map<String,Integer> stringIntegerMap1 = (Map<String,Integer>)stringIntegerMap;
                switch(stringIntegerMap1.get("type")){
                    case 0:list.get(0).add(stringIntegerMap1);break;
                    case 1:list.get(1).add(stringIntegerMap1);break;
                    case 2:list.get(2).add(stringIntegerMap1);break;
                }
            }
            return list;
        }
}
