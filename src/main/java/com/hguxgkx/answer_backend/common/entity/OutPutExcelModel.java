package com.hguxgkx.answer_backend.common.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutPutExcelModel {
    @ExcelProperty(value = "姓名", index = 0)
    private String name;
    @ExcelProperty(value = "学院", index = 1)
    private String roles;
    @ExcelProperty(value = "学号", index = 2)
    private String id;
    @ExcelProperty(value = "成绩", index = 3)
    private int score;
    @ExcelProperty(value = "班级", index = 4)
    private String classes;
}
