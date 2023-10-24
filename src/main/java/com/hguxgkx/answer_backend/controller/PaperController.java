package com.hguxgkx.answer_backend.controller;

import com.alibaba.excel.EasyExcel;
import com.hguxgkx.answer_backend.common.entity.OutPutExcelModel;
import com.hguxgkx.answer_backend.common.entity.R;
import com.hguxgkx.answer_backend.common.entity.TextQuestionExcelModel;
import com.hguxgkx.answer_backend.config.FileConfigPropreties;
import com.hguxgkx.answer_backend.config.JwtConfigPropreties;
import com.hguxgkx.answer_backend.service.serviceImpl.PaperServiceImpl;
import com.hguxgkx.answer_backend.utils.JwtUtils;
import com.hguxgkx.answer_backend.utils.ModelExcelListener;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    PaperServiceImpl s;
    @Autowired
    JwtConfigPropreties jwtConfigPropreties;
    @Autowired
    FileConfigPropreties fileConfigPropreties;

    /*
    * 获取模板的controller
    * url:/paper/download_template
    * */
    @GetMapping(value = "/download_template")
    public ResponseEntity<Resource> downloadTemplate(){
        String contentDisposition = ContentDisposition
                .builder("attachment")
                .filename("template.xlsx") //用户下载的文件名
                .build().toString();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(fileConfigPropreties.getPath()));//文件路径
    }

    /*
    * 提交excel文件返回试题列表的controller
    * url:/paper/upload_template
    * 参数:file:文件
    * result:{
        "question_list": [
            {
                "score": 分数,
                "answer": [
                    0
                ],
                "options": [
                    "A",
                    "B",
                    "C",
                    "D"
                ],
                "type": 0,
                "stem": "单选题干1"
            },...]
        "paper_id": 4
       }
    * */
    @PostMapping("/upload_template")
    public R uploadTemplate(@RequestParam("file") MultipartFile file,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        return s.uploadTemplate(file,JwtUtils.analysisToken(request,response,jwtConfigPropreties.getSignature()));
    }


    /*
    * 发布试卷的controller
    * url:/paper/public_paper
    * 参数:{
        "paper_id": 试卷id,
        "paper_name": "试卷名",
        "random": 是否随机(布尔值),
        "type": ["学院名2","学院名1"]
    }
    *  "result": 无
    * */
    @PostMapping("/public_paper")
    public R publicPaper(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody Map<String,Object> map) throws IOException {
        return s.publicPaper(JwtUtils.analysisToken(request,response,jwtConfigPropreties.getSignature()),map);
    }

    /*
    * 提交excel文件返回试题列表的controller
    * url:/paper/get_paper_info
    * 参数:{"paper_id":试卷id}
    * result:{
        "question_list": [
            {
                "score": 分数,
                "answer": [
                    0
                ],
                "options": [
                    "A",
                    "B",
                    "C",
                    "D"
                ],
                "type": 0,
                "stem": "单选题干1"
            },...]
        "paper_id": 4
       }
    * */
    @PostMapping("/get_paper_info")
    public R getPaperInfo(@RequestBody Map<String,Object> map){
        return s.getPaperInfo((int)map.get("paper_id"));
    }

    /*
    * 获取用户发布的试卷列表的controller
    * url:/paper/get_paper_list
    * 参数:无
    *  "result": [{
            "end_exam": 完成考试的人数,
            "paper_name": "试卷名称",
            "all_exam": 所有参加考试的人数,
            "publish": 试卷是否公开发布(0,1),
            "id": 试卷编号
        },
    * ]
    * */
    @GetMapping("/get_paper_list")
    public R getPaperList(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        return s.getPaperList(JwtUtils.analysisToken(request,response,jwtConfigPropreties.getSignature()));
    }


    /*
    * 结束考试的接口
    * url:/paper/end_exam
    * 参数:{paper_id: 试卷id}
    * result:无
    * */
    @PostMapping("/end_exam")
    public R endExam(@RequestBody Map<String,Integer> map){
        return s.endExam(map.get("paper_id"));
    }

    /**
     * 导出数据
     * url:/paper/out_data
     * 参数:{
     *      "paper_id":试卷id
     *      }
     */
    @PostMapping("/out_data")
    public void outData(HttpServletResponse response,@RequestBody Map<String,Object> map) throws Exception {
        XSSFWorkbook workbook = s.outData((Integer) map.get("paper_id"));
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("考试"+".xlsx", "utf-8"));
        response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
