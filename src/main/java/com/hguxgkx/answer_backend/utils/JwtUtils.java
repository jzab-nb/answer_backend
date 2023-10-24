package com.hguxgkx.answer_backend.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hguxgkx.answer_backend.config.JwtConfigPropreties;
import com.hguxgkx.answer_backend.common.entity.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Autowired
    JwtConfigPropreties jwtConfigPropreties;
    /*
    * 生成token的函数,token的payload包含学生的id
    * */
    public static String createToken(String id, Integer exp,String signature){
        // 假设代码块被包含在一个函数中
        Map<String, Object> claims = new HashMap<>();
        // claims就是playload中的内容，可以写入自己需要的键值
        claims.put("id", id);
        // 构建
        JwtBuilder jwtBuilder = Jwts.builder()
                // 设置有效载荷
                .setClaims(claims)
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis()+1000*exp))
                // 采用HS256方式签名，key就是用来签名的秘钥
                .signWith(SignatureAlgorithm.HS256,signature);
        // 调用compact函数将token打包成String并返回
        return jwtBuilder.compact();
    }

    /*
    * 解析token的函数
    * */
    public static String analysisToken(HttpServletRequest request, HttpServletResponse response, String signature) throws IOException {
        String token = request.getHeader("token");
        Claims claims;
        String r;
        // token不一定通过验证，所以需要包裹try-catch捕获jjwt提供的JwtException
        try {
            claims = Jwts.parser()
                    // HS256是对称加密体系，加密解密使用同一个key
                    .setSigningKey(signature)
                    .parseClaimsJws(token)
                    .getBody();
            r = claims.get("id",String.class);
        } catch (Exception e) {
            ServletUtils.returnJson(response, JSON.toJSONString(R.TokenError()));
            r = null;
            e.printStackTrace();
        }
        return r;
    }
}
