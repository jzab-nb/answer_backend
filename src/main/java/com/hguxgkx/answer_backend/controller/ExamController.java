package com.hguxgkx.answer_backend.controller;

import com.hguxgkx.answer_backend.common.entity.R;
import com.hguxgkx.answer_backend.config.JwtConfigPropreties;
import com.hguxgkx.answer_backend.service.serviceImpl.ExamServiceImpl;
import com.hguxgkx.answer_backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    ExamServiceImpl s;
    @Autowired
    JwtConfigPropreties jwtConfigPropreties;

    /*
     * 根据学号获取考试列表的的controller
     * url:/exam/get_exam_list
     * 参数:无
     * result:[
            {
                "end_exam": 是否完成考试(0,1),
                "score": 分数,
                "paper_name": "试卷名",
                "end_time": "开始时间",
                "open_time": "结束时间",
                "end_judge": 是否完成判卷(0,1),
                "id": 考试编号,
                "question_number": 题目数量
            },...
     * ]
     * */
    @GetMapping("/get_exam_list")
    public R getExamList(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        return s.getExamList(JwtUtils.analysisToken(request,response,jwtConfigPropreties.getSignature()));
    }

    /*
     * 根据题号和考试号获取题目的controller
     * url:/exam/get_question
     * 参数:{
            "question_id":题号,从0开始,
            "exam_id":考试号,从1开始,获取考试列表的请求有返回
        }
     *  "result": {
            "chose": ["选项1","选项2",...],
            "type": 题目类型(0单选,1多选,2简答),
            "stem": "题干"
        },
     * */
    @PostMapping("/get_question")
    public R getQuestion(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestBody Map<String,Integer> map) throws IOException {
        return s.getQuestion(
                map.get("question_id"),
                JwtUtils.analysisToken(request,response,jwtConfigPropreties.getSignature()),
                map.get("exam_id"));
    }

    /*
     * 上传答案的controller
     * url:/exam/submit_answer
     * 参数:{
            "exam_id":1,
            "answer_list":[1,1,2]
        }
     *  "result": 分数
     * */
    @PostMapping("/submit_answer")
    public R submitAnswer(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map<String, Object> map) throws IOException {
        return s.submitAnswer((List<Object>) map.get("answer_list"),
                JwtUtils.analysisToken(request,response,jwtConfigPropreties.getSignature()),
                (Integer) map.get("exam_id"));
    }

    /*
    * 获取单个试卷下所有学生的信息
    * url:/exam/get_student_list
    * 参数:{"id":试卷id}
    *  "result": [{
            "name":学生姓名,
            "student_id":学号,
            "score":考试分数,
            "end_exam":是否完成考试,
            "id":考试编号
        },
    * ]
    * */
    @PostMapping("/get_student_list")
    public R getStudentList(@RequestBody Map<String, Object> map){
        return s.getStudentList((Integer) map.get("id"));
    }

    /*
    * 获取一场考试(具体到学生的所有信息)
    * url:/exam/get_exam_info
    * 参数:{"id":考试id}
    *  "result": {
            "name":学生姓名,
            "student_id":学号,
            "score":考试分数,
            "end_exam":是否完成考试,
            "true_false_list":[],
            "answer_list":[],
            "question_list":[]
        }
    * */
    @PostMapping("/get_exam_info")
    public R getExamInfo(@RequestBody Map<String, Object> map){
        return s.getExamInfo((Integer) map.get("id"));
    }
}
