package com.hguxgkx.answer_backend.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtils {
    /*
    * 手动生成一个json类型的响应,用于统一的拦截器
    * */
    public static void returnJson(HttpServletResponse response,Object o) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(o);
    }
}
