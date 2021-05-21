package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.Permission;
import com.wxj.work.mapper.PermissionMapper;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    public Result queryPerrmission(String userId){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject=JSON.parseObject(userId);
            Long permissionUserId=jsonObject.getLong("userId");
            Permission selectPermission=permissionMapper.queryPerrmission(permissionUserId);
            if(selectPermission!=null){
                int permissionValue=selectPermission.getPermissionValue();
                result.setSuccess(true);
                result.setDetail(permissionValue);
                result.setMsg("查询权限成功");
            }else {
                result.setMsg("查询权限失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public Result queryAllUserPermission(String username){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject=JSON.parseObject(username);
            Long number=jsonObject.getLong("username");
            List<Permission> permissionList=permissionMapper.permissionList(number);
            if(permissionList!=null|!permissionList.isEmpty()|permissionList.equals("")){
                result.setMsg("查询所有权限成功");
                result.setSuccess(true);
                result.setDetail(permissionList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result updatePermission(Permission permission){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            permission.setCreatePermissionTime(Long.toString(System.currentTimeMillis()/1000L));
            int updatePermission= permissionMapper.updatePermission(permission);
            if(updatePermission>0){
                result.setSuccess(true);
                result.setMsg("修改权限成功");
                result.setDetail(updatePermission);
            }else {
                result.setMsg("修改权限失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
