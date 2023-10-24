package com.hguxgkx.answer_backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
 * jjwt相关配置,签名,过期时间(s)
 * */
@Data
@Component
public class JwtConfigPropreties {
    @Value("${jwt.signature}")
    private String signature;
    @Value("${jwt.exp}")
    private Integer exp;
}