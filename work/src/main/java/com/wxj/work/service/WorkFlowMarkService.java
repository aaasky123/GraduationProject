package com.wxj.work.service;

import com.wxj.work.entity.WorkFlowMark;
import com.wxj.work.mapper.WorkFlowMarkMapper;
import com.wxj.work.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowMarkService {
    @Autowired
    private WorkFlowMarkMapper workFlowMarkMapper;

    public Result addMark(WorkFlowMark workFlowMark){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            int addWorkFlowMark= workFlowMarkMapper.addMark(workFlowMark);
            if(addWorkFlowMark>0){
                result.setSuccess(true);
                result.setMsg("增加批注成功");
                result.setDetail(addWorkFlowMark);
            }else {
                result.setMsg("增加批注失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result queryWorkFlowMark(WorkFlowMark workFlowMark){
        Result result=new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            List<WorkFlowMark> allWorkFlowMark=workFlowMarkMapper.queryMark(workFlowMark);
            if(allWorkFlowMark!=null){
                result.setMsg("查询批注成功");
                result.setSuccess(true);
                result.setDetail(allWorkFlowMark);
            }else{
                result.setMsg("查询批注失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
