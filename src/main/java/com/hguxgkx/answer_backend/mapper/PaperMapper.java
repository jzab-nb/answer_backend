package com.hguxgkx.answer_backend.mapper;

import com.hguxgkx.answer_backend.pojo.Paper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaperMapper {
    /*
    * 增加试卷
    * */
    int addPaper(Paper paper);
    /*
    * 假删除试卷(本质是改变is_delete字段的值)
    * */
    int deletePaper(int id);
    /*
    * 更改题目列表
    * Map传参{"questionsList":list,"id":id}
    * */
    int changeQuestionsList(Map<String,Object> map);
    /*
    * 更改分数列表
    * Map传参{"customScore":list,"id":id}
    * */
    int changeCustomScore(Map<String,Object> map);

    /*
    * 更改试卷状态
    * 1.发布试卷
    * 2.开始时间{"openTime":date,"id":id}
    * 3.结束时间{"endTime":date,"id":id}
    * 4.是否随机(0:不随机,1:部分随机,2:全随机){"random":random,"id":id}
    * */
    int publishPaper(Map<String,Object> map);

    int changeOpenTime(Map<String,Object> map);

    int changeEndTime(Map<String,Object> map);

    int changeRandom(Map<String,Object> map);

    int endExam(int id);

    /*
    * 根据id查询试卷
    * */
    Paper getPaperById(int id);

    /*
    * 根据作者查询试卷的部分信息(不包括假删除的)
    * */
    List<Map<String,Object>> getPaperByAuthorId(String id);

    /*
    * 根据面向的群体获取试卷
    * */
    List<Paper> getPaperByFace(String face);

    /*
    * 查询试卷id的最大值
    * */
    int getMaxId();
}
