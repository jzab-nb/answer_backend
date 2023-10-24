package com.hguxgkx.answer_backend.common.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class TextQuestionExcelModel  extends BaseRowModel implements Serializable {
    @ExcelProperty(value = "题干", index = 0)
    private String stem;

    @ExcelProperty(value = "答案", index = 1)
    private String answer;

    @ExcelProperty(value = "分值", index = 2)
    private Integer score;

    @Override
    public String toString() {
        return "TextQuestionExcelModel{" +
                "stem='" + stem + '\'' +
                ", answer='" + answer + '\'' +
                ", score=" + score +
                '}';
    }
}
