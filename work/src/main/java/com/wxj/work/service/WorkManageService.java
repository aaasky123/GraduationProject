package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.WorkManage;
import com.wxj.work.mapper.UserMapper;
import com.wxj.work.mapper.WorkManageMapper;
import com.wxj.work.util.Result;
import com.wxj.work.vo.userVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkManageService {
    @Autowired
    private WorkManageMapper workManageMapper;
    @Autowired
    private UserMapper userMapper;

    public Result queryManage(WorkManage workManage){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            List<WorkManage> workManageList=workManageMapper.queryManage(workManage);
            if(!workManageList.isEmpty()){
                result.setSuccess(true);
                result.setMsg("人员管理查询成功");
                result.setDetail(workManageList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result addManage(String addManageData){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject= JSON.parseObject(addManageData);
            String userId=jsonObject.getString("userId");
            userVo userInformation=userMapper.queryUserInformation(userId);
            String userCompanyId=userInformation.getCompanyId();

            String manageId= jsonObject.getString("manageId");
            userVo manageInformation=userMapper.queryUserInformation(manageId);
            String manageCompanyId=manageInformation.getCompanyId();

            String companyId= jsonObject.getString("companyId");
            if(userCompanyId.equals(companyId)&&manageCompanyId.equals(companyId)){
                WorkManage workManage=new WorkManage();
                workManage.setUserId(Long.parseLong(userId));
                workManage.setManageId(Long.parseLong(manageId));
                workManage.setCompanyId(Long.parseLong(companyId));
                int addWorkManage= workManageMapper.addManage(workManage);
                if(addWorkManage>0){
                    result.setSuccess(true);
                    result.setMsg("添加人员管理成功");
                    result.setDetail(addWorkManage);
                }
                else {
                    result.setMsg("添加人员管理失败");
                }
            }else {
                result.setMsg("添加人员管理失败");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result editManage(WorkManage workManage){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            Long manageUserId=workManage.getManageId();
            userVo manageUserInformaton= userMapper.queryUserInformation(manageUserId.toString());
            Long manageUserCompanyId=Long.parseLong(manageUserInformaton.getCompanyId());
            if(workManage.getCompanyId().equals(manageUserCompanyId)){
                int editManage=workManageMapper.editManage(workManage);
                if(editManage>0){
                    result.setSuccess(true);
                    result.setMsg("修改人员管理成功");
                    result.setDetail(editManage);
                }else {
                    result.setMsg("修改人员管理失败");
                }
            }else {
                result.setMsg("修改人员管理失败");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result delManage(WorkManage workManage){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            int delManage=workManageMapper.delManage(workManage);
            if(delManage>0){
                result.setSuccess(true);
                result.setMsg("删除人员管理成功");
                result.setDetail(delManage);
            }else {
                result.setMsg("删除人员管理成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
