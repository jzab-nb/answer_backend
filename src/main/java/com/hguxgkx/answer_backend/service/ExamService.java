package com.hguxgkx.answer_backend.service;

import com.hguxgkx.answer_backend.common.entity.R;

import java.util.List;

public interface ExamService {
    /*
    * 根据学号获取该学生的所有考试信息
    * */
    public R getExamList(String id);

    /*
    * 获取题目,根据试卷id和题号
    * */
    public R getQuestion(int questionId,String studentId,int examId);

    /*
    * 上传答案计算选择题分数
    * */
    public R submitAnswer(List<Object> list, String studentId, int examId);

    /*
    * 根据试卷号获取参与考试的所有学生的信息
    * */
    public R getStudentList(int paperId);

    /*
    * 根据考试号查询学生答题的详细
    * */
    public R getExamInfo(int examId);
}
