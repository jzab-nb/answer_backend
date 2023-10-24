package com.hguxgkx.answer_backend.mapper;

import com.hguxgkx.answer_backend.pojo.Exam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExamMapper {
    /*
    * 增加一个新的考试
    * */
    int addExam(Exam exam);

    /*
    * 删除一个考试
    * */
    int deleteExam(int id);

    /*
    * 删除一些考试根据试卷id
    * */
    int deleteExamByPaperId(int id);

    /*
    * 为考试上传答案(map传参)
    * 参数:{answersList: [1,2,3...],id:1}
    * */
    int uploadAnswers(Map<String,Object> map);

    /*
    * 更改考试的状态(map传参)
    * 1.是否考完,{endExam: 1,id:1}
    * 2.是否判完卷,{endJudge: 1,id:1}
    * 3.分数,{score: 1,id:1}
    * 4.正确/错误列表,{trueFalseList: [],id:1}
    * */
    int changeEndExam(Map<String,Object> map);

    int changeEndJudge(Map<String,Object> map);

    int changeScore(Map<String,Object> map);

    int changeTrueFalseList(Map<String,Object> map);

    /*
    * 根据id查询考试
    * */
    Exam getExamById(int id);

    /*
    * 根据试卷编号查询考试
    * */
    List<Map<String,Object>> getExamByPaperId(int id);

    /*
    * 根据学号查询一个学生的所有考试
    * */
    List<Map<String,Object>> getExamByStudentId(String id);

    /*
    * 根据试卷编号查询完成考试的人数
    * */
    int countEndExam(int id);

    /*
    * 根据试卷编号查询总人数
    * */
    int countAll(int id);
}
