package com.hguxgkx.answer_backend.service;

import com.hguxgkx.answer_backend.common.entity.R;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PaperService {
    /*
    *接收excel文件,处理并返回
    * */
    R uploadTemplate(MultipartFile file, String id) throws IOException;

    /*
    * 发布试卷
    * */
    R publicPaper(String author_id, Map<String,Object> map);

    /*
    * 获取一个试卷的预览
    * */
    R getPaperInfo(int id);

    /*
    * 返回用户发布的试卷列表
    * */
    R getPaperList(String id);

    /*
     * 结束考试
     * */
    public R endExam(int paperId);

    /*
    * 返回试卷的答题情况
    * */
    XSSFWorkbook outData(int paperId);
}
