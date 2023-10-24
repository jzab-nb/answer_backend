package com.hguxgkx.answer_backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class FileConfigPropreties {

    @Value("${file.excelPath}")
    private String path;
}
