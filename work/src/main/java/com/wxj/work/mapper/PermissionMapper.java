package com.wxj.work.mapper;

import com.wxj.work.entity.Permission;
import com.wxj.work.entity.PermissionExample;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface PermissionMapper {
    long countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    /**
     * 查找权限
     */
    @Select("select permission_value from permission where user_id=#{userId};")
    Permission queryPerrmission(Long permissionUserId);

    /**
     * 注册时增加权限
     */
    @Insert("insert into permission values(0, #{userId}, #{createAdminId}, now(), 0);")
    int insertWhenRegister(Permission permission);

    /**
     * 查找公司所有员工的权限
     */
    @Select("select * from permission where user_id in(select number from user where company_id=(select company_id from user where number=#{number}))")
    List<Permission> permissionList(@Param("number")Long number);

    /**
     * 管理员更新权限
     */
    @Update("update permission set create_admin_id=#{createAdminId}, create_permission_time=now(), permission_value=#{permissionValue} where user_id=#{userId};")
    int updatePermission(Permission permission);
}