package com.hguxgkx.answer_backend.mapper;

import com.hguxgkx.answer_backend.pojo.Roles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolesMapper {
    /*
    * 查找角色列表
    * */
    List<Roles> getRolesList();
    /*
    * 查找角色根据id
    * */
    Roles getRolesByID(int id);
    /*
    * 根据名字查找角色
    * */
    Roles getRolesByName(String name);
}
