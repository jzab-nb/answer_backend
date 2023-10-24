package com.hguxgkx.answer_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.hguxgkx.answer_backend.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    private JwtConfigPropreties jwtConfigPropreties;
    /*
    * 拦截器
    * */
    public LoginHandlerInterceptor(JwtConfigPropreties jwtConfigPropreties){
        this.jwtConfigPropreties = jwtConfigPropreties;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("拦截器生效"+request.getRequestURL() );
        //找token
        return JwtUtils.analysisToken(request, response, jwtConfigPropreties.getSignature( )) != null;
    }
}