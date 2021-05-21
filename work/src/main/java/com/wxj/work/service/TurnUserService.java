package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.TurnUser;
import com.wxj.work.entity.User;
import com.wxj.work.mapper.TurnUserMapper;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TurnUserService {
    @Autowired
    private TurnUserMapper turnUserMapper;

    public Result addCommonTurnUser(TurnUser turnUser){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            List<TurnUser> queryCommonTurnUser=turnUserMapper.queryisCTUinList(turnUser);
            if(queryCommonTurnUser==null||queryCommonTurnUser.isEmpty()){
                int addCommonTurnUser=turnUserMapper.insertCommonTUrnUser(turnUser);
                if(addCommonTurnUser>0){
                    result.setMsg("添加常用移交人成功");
                    result.setSuccess(true);
                    result.setDetail(addCommonTurnUser);
                }else{
                    result.setMsg("添加常用移交人失败");
                }
            }else{
                result.setMsg("已添加此常用移交人");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryCommonTurnUserList(String number){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            JSONObject jsonObject= JSON.parseObject(number);
            String loginUserNumber=jsonObject.getString("number");
            List<User> queryCommonTurnUserList=turnUserMapper.queryCommonTurnUserList(loginUserNumber);
            if(queryCommonTurnUserList==null){
                result.setMsg("查询失败");
            }else{
                result.setMsg("查询成功");
                result.setSuccess(true);
                result.setDetail(queryCommonTurnUserList);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result delCommonTurnUser(TurnUser turnUser){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            int deleteCommonTurnUser= turnUserMapper.deleteCommonTurnUser(turnUser);
            if(deleteCommonTurnUser>0){
                result.setMsg("删除成功");
                result.setSuccess(true);
                result.setDetail(deleteCommonTurnUser);
            }else {
                result.setMsg("删除失败");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
