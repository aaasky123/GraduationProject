package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.Company;
import com.wxj.work.mapper.CompanyMapper;
import com.wxj.work.mapper.UserMapper;
import com.wxj.work.util.Result;
import com.wxj.work.vo.userVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private UserMapper userMapper;

    public Result registerCompany(Company company){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            List<Company> selectCompanyById=companyMapper.selectCompanyById(company);
            List<Company> selectCompanyByName=companyMapper.selectCompanyByName(company);
            if(selectCompanyById.isEmpty()&&selectCompanyByName.isEmpty()){
                int registerCompany=companyMapper.registerCompany(company);
                if(registerCompany>0){
                    result.setMsg("注册公司成功");
                    result.setSuccess(true);
                    result.setDetail(registerCompany);
                }else{
                    result.setMsg("注册公司失败");
                }
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryAdmin(Company company){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            List<Company> queryAdmin=companyMapper.queryCompanyAdmin(company);
            if(!queryAdmin.isEmpty()){
                result.setSuccess(true);
                result.setMsg("查询管理员成功");
                result.setDetail(queryAdmin);
            }else {
                result.setMsg("查询管理员失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result addAdmin(String addAdminData){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject= JSON.parseObject(addAdminData);
            Long number= jsonObject.getLong("number");
            Long companyId= jsonObject.getLong("companyId");
            Company adminInformation= companyMapper.queryAdmin(number, companyId);

            String addAdminId=jsonObject.getString("adminId");
            userVo getCompanyId= userMapper.queryUserInformation(addAdminId);
            if(companyId.equals(Long.parseLong(getCompanyId.getCompanyId()))){
                Long adminId=Long.parseLong(addAdminId);
                adminInformation.setAdminId(adminId);
                int addAdmin=companyMapper.addAdmin(adminInformation);
                if(addAdmin>0){
                    result.setSuccess(true);
                    result.setMsg("添加管理员成功");
                    result.setDetail(addAdmin);
                }else {
                    result.setMsg("添加管理员失败");
                }
            }else {
                result.setMsg("添加管理员失败");
            }
        } catch (NumberFormatException e) {
            result.setMsg("添加管理员失败");
            e.printStackTrace();
        }
        return result;
    }

    public Result delAdmin(Company company){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            int delAdmin=companyMapper.delAdmin(company);
            if(delAdmin>0){
                result.setSuccess(true);
                result.setMsg("删除管理员成功");
                result.setDetail(delAdmin);
            }else {
                result.setMsg("删除管理员失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
