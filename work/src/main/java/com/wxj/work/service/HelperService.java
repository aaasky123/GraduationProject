package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.Helper;
import com.wxj.work.mapper.HelperMapper;
import com.wxj.work.util.Result;
import com.wxj.work.vo.HelperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelperService {

    @Autowired
    private HelperMapper helperMapper;

    public Result addHelper(Helper helper){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            int addHelper= helperMapper.addHelper(helper);
            if(addHelper>0){
                result.setMsg("添加协办人成功");
                result.setSuccess(true);
                result.setDetail(addHelper);
            }
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result queryHelper(Helper helper){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            List<HelperVo> queryHelper=helperMapper.queryHelper(helper);
            if(queryHelper==null){
                result.setMsg("查询失败");
            }else {
                result.setMsg("查询成功");
                result.setSuccess(true);
                result.setDetail(queryHelper);
            }
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result deleteHelper(String helperId){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            JSONObject jsonObject= JSON.parseObject(helperId);
            Integer id=jsonObject.getInteger("id");
            int delHelper= helperMapper.deleteHelper(id);
            if(delHelper>0){
                result.setMsg("删除协办人成功");
                result.setSuccess(true);
                result.setDetail(delHelper);
            }
        }catch (Exception e){
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
