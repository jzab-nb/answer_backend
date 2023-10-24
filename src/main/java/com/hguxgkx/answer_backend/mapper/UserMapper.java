package com.hguxgkx.answer_backend.mapper;

import com.hguxgkx.answer_backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /*
    * 增加用户
    * */
    int addUser(User user);

    /*
    * 更新用户的考试列表
    * map传参:{"exams":[1,2,3...],"id":"123456"}
    * */
    int updateExamList(Map<String,Object> map);

    /*
     * 查找用户根据id
     * */
    User getUserById(String id);

    String getMaxId();

    /*
    * 根据角色获取用户
    * */
    List<User> getUserByRoles(int role);

    /*
     * 获得用户的考试列表
     * */
    Map<String,Integer> getExamList(String id);

}
