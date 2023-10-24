package com.hguxgkx.answer_backend.common.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserExcelModel extends BaseRowModel implements Serializable {
    @ExcelProperty(value = "学号", index = 0)
    private String stuId;

    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    @ExcelProperty(value = "班级", index = 2)
    private String classes;
}
