package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.Company;
import com.wxj.work.entity.Permission;
import com.wxj.work.entity.User;
import com.wxj.work.mapper.CompanyMapper;
import com.wxj.work.mapper.PermissionMapper;
import com.wxj.work.mapper.UserMapper;
import com.wxj.work.util.Result;
import com.wxj.work.vo.userVo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public Result login(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            Long haveNumber = userMapper.login(user);
            if(haveNumber==null){
                result.setMsg("用户名或密码错误");
            }else {
                result.setMsg("登录成功");
                result.setSuccess(true);
                user.setNumber(haveNumber);
                User userNumber= userMapper.findUserByNumber(haveNumber);
                result.setDetail(userNumber);
            }

        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryUserInformation(String number){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject= JSON.parseObject(number);
            String username=jsonObject.getString("number");
            userVo haveInformation = userMapper.queryUserInformation(username);
            if (haveInformation==null){
                result.setMsg("查询失败");
            }else{
                result.setMsg("查询成功");
                result.setSuccess(true);
                result.setDetail(haveInformation);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result changePassword(String number, String oldPassword, String newPassword){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            JSONObject jsonObject=JSON.parseObject(number);
            number=jsonObject.getString("number");
            oldPassword=jsonObject.getString("oldPassword");
            newPassword=jsonObject.getString("newPassword");
            int changepassword=userMapper.changePassword(number, oldPassword, newPassword);
            if(changepassword<0){
                result.setMsg("修改失败");
            }else{
                result.setMsg("修改成功");
                result.setSuccess(true);
                result.setDetail(changepassword);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result registerUser(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            int registeruser=userMapper.registerUser(user);
            if (registeruser < 0) {
                result.setMsg("注册失败");
            }else{
                Permission permission=new Permission();
                permission.setUserId(user.getNumber());
                permission.setCreateAdminId(user.getNumber());
                int insertWhenRegister= permissionMapper.insertWhenRegister(permission);
                result.setMsg("注册成功");
                result.setSuccess(true);
                result.setDetail(String.valueOf(user.getNumber()));
            }
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryCompanyUser(String number){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            JSONObject jsonObject=JSON.parseObject(number);
            String userNumber=jsonObject.getString("number");
            List<User> queryCompanyAllUser= userMapper.queryCompanyUser(userNumber);
            if(queryCompanyAllUser==null){
                result.setMsg("查询失败");
            }else{
                result.setMsg("查询成功");
                result.setSuccess(true);
                result.setDetail(queryCompanyAllUser);
            }
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result updateCompanyId(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            int companyId=user.getCompanyId();
            Integer queryMaxEmployeeId=userMapper.queryMaxEmployeeId(companyId);
            Integer employeeId=queryMaxEmployeeId+1;
            user.setEmployeeId(employeeId);
            int updateUserCompanyId= userMapper.updateCompanyId(user);
            if(updateUserCompanyId>0){
                result.setMsg("加入公司成功");
                result.setSuccess(true);
                result.setDetail(updateUserCompanyId);
            }
            else {
                result.setMsg("加入公司失败");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result updateUserInformation(String data){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            JSONObject jsonObject=JSON.parseObject(data);
            Long number=Long.parseLong(jsonObject.getString("number"));
            String realName= jsonObject.getString("realname");
            String email= jsonObject.getString("email");
            int updateInformation= userMapper.updateInformation(realName, email, number);
            if(updateInformation>0){
                result.setSuccess(true);
                result.setMsg("更改个人信息成功");
                result.setDetail(updateInformation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result updateForgetPassword(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            int updatePassword= userMapper.updateForgetPassword(user);
            if(updatePassword>0){
                result.setSuccess(true);
                result.setMsg("修改密码成功");
                result.setDetail(updatePassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result loginByAdmin(String loginData){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            System.out.println(loginData);
            JSONObject jsonObject=JSON.parseObject(loginData);
            Long number=Long.parseLong(jsonObject.getString("number"));
            Long companyId=Long.parseLong(jsonObject.getString("companyId"));
            String password= jsonObject.getString("password");
            Company admin=companyMapper.queryAdmin(number,companyId);
            if(admin!=null|!admin.equals("")){
                Long haveNumber = userMapper.adminLogin(number, password);
                if(haveNumber!=null){
                    result.setMsg("管理员登录成功");
                    result.setSuccess(true);
                    result.setDetail(haveNumber);
                }else {
                    result.setMsg("管理员登录失败");
                }
            }else{
                result.setMsg("管理员登录失败");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result delCompanyId(User user){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            int delCompanyId=userMapper.delCompanyId(user);
            if(delCompanyId>0){
                result.setSuccess(true);
                result.setMsg("删除用户成功");
                result.setDetail(delCompanyId);
            }else{
                result.setMsg("删除用户失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
