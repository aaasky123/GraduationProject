package com.wxj.work.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.entity.Notifications;
import com.wxj.work.mapper.NotificationsMapper;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsService {
    @Autowired
    private NotificationsMapper notificationsMapper;

    public Result queryNotifications(String userId){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            JSONObject jsonObject= JSON.parseObject(userId);
            Long userIdOfQuery=jsonObject.getLong("userId");
            List<Notifications> queryNotifications=notificationsMapper.queryNotifications(userIdOfQuery);
            if(queryNotifications!=null){
                result.setSuccess(true);
                result.setMsg("查找通知成功");
                result.setDetail(queryNotifications);
            }else {
                result.setMsg("暂无通知");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result addNotifications(Notifications notifications){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try{
            int addNotification= notificationsMapper.addNotifications(notifications);
            if(addNotification>0){
                result.setSuccess(true);
                result.setMsg("发布通知成功");
                result.setDetail(addNotification);
            }else {
                result.setMsg("发布通知失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
