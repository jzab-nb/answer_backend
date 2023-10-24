package com.hguxgkx.answer_backend.common.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class MultipleChoiceExcelModel  extends BaseRowModel implements Serializable {
    @ExcelProperty(value = "题干", index = 0)
    private String stem;

    @ExcelProperty(value = "选项A", index = 1)
    private String optionA;

    @ExcelProperty(value = "选项B", index = 2)
    private String optionB;

    @ExcelProperty(value = "选项C", index = 3)
    private String optionC;

    @ExcelProperty(value = "选项D", index = 4)
    private String optionD;

    @ExcelProperty(value = "选项E", index = 5)
    private String optionE;

    @ExcelProperty(value = "选项F", index = 6)
    private String optionF;

    @ExcelProperty(value = "选项G", index = 7)
    private String optionG;

    @ExcelProperty(value = "答案", index = 8)
    private String answer;

    @ExcelProperty(value = "分值", index = 9)
    private Integer score;
}
