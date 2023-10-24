package com.hguxgkx.answer_backend.service;

import com.hguxgkx.answer_backend.common.entity.R;

/*
* 主要处理用户相关的业务
* */
public interface UserService {
    /*
    * 登录,接受id姓名,校验正确与否
    * */
    R login(String id,String name);

    /*
    * 注册,通过姓名生成账号
    * */
    R register(String name);

    /*
    * 导入学生信息
    * */
    R input();
}
