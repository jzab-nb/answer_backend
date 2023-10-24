package com.hguxgkx.answer_backend.controller;

import com.hguxgkx.answer_backend.common.entity.R;
import com.hguxgkx.answer_backend.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserServiceImpl s;

    /*
    * 登录的controller
    * url="/user/login"
    * 参数:{name:"姓名",id:"学号"}
    * result:{token:"token",roles:角色号}
    * */
    @PostMapping("/user/login")
    @ResponseBody
    public R login(@RequestBody Map<String,String> map){
        return s.login(map.get("id"), map.get("name"));
    }

    /*
     * 注册的controller
     * url="/user/register"
     * 参数:{name:"姓名"}
     * result:{token:"token",roles:角色号}
     * */
    @PostMapping("/user/register")
    @ResponseBody
    public R register(@RequestBody Map<String,String> map){
        return s.register(map.get("name"));
    }
}
