package com.hguxgkx.answer_backend.mapper;

import com.hguxgkx.answer_backend.pojo.TextQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TextQuestionMapper {
    /*
     * 增加题目
     * */
    int addQuestion(TextQuestion textQuestion);
    /*
     * 假删除题目
     * */
    int deleteQuestion(int id);

    /*
     * 更改题目内容
     * */

    /*
     * 根据id查询
     * */
    TextQuestion getQuestionById(int id);

    /*
     * 根据作者查询
     * */
    List<TextQuestion> getQuestionByAuthorId(String id);

    /*
     * 获取最大的id
     * */
    int getMaxId();
}
