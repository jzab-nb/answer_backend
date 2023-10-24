package com.hguxgkx.answer_backend.mapper;

import com.hguxgkx.answer_backend.pojo.ChoiceQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChoiceQuestionMapper {
    /*
    * 增加题目
    * */
    int addQuestion(ChoiceQuestion choiceQuestion);
    /*
    * 假删除题目
    * */
    int deleteQuestion(int id);

    /*
    * 更改题目内容(题干,单多选)
    * */

    /*
    * 根据id查询
    * */
    ChoiceQuestion getQuestionById(int id);

    /*
    * 根据作者查询
    * */
    List<ChoiceQuestion> getQuestionByAuthorId(String id);

    /*
    * 获取最大的id
    * */
    int getMaxId();
}
