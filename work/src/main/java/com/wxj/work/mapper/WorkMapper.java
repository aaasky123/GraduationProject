package com.wxj.work.mapper;

import com.wxj.work.entity.User;
import com.wxj.work.entity.Work;
import com.wxj.work.entity.WorkExample;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface WorkMapper {
    long countByExample(WorkExample example);

    int deleteByExample(WorkExample example);

    int deleteByPrimaryKey(Long workId);

    int insert(Work record);

    int insertSelective(Work record);

    List<Work> selectByExample(WorkExample example);

    Work selectByPrimaryKey(Long workId);

    int updateByExampleSelective(@Param("record") Work record, @Param("example") WorkExample example);

    int updateByExample(@Param("record") Work record, @Param("example") WorkExample example);

    int updateByPrimaryKeySelective(Work record);

    int updateByPrimaryKey(Work record);

    /**
     * 查找companyId
     */
    @Select("select * from user where number=#{number}")
    @ResultType(User.class)
    User queryCompanyId(Long number);

    /**
     * 创建新工作
     */
    int createNewWork(Work work);

    /**
     * 权限1查看所有进行中工作，只有查看权限的用户查看所有进行中工作
     */
    List<Work> queryAllOnWorkingWorkPermission1(@Param("number")String loginUserNumber);

    /**
     * 权限1查看所有已完结工作，只有查看权限的用户查看所有已完结工作
     */
    List<Work> queryAllFinishedWorkPermission1(@Param("number")String number);

    /**
     * 权限2，只有批注权限的用户查看所有进行中工作,即只能查看参与的进行中工作
     */
    List<Work> queryJoinInOnWorkingWork(@Param("number")String number);

    /**
     * 权限2，只有批注权限的用户查看所有已结束工作,即只能查看参与的已结束工作
     */
    List<Work> queryJoinInFinishedWork(@Param("number")String number);

    /**
     * 完结工作
     */
    @Update("update work set end_time=#{endTime}, state=1 where work_id=#{workId};")
    int finishWork(@Param("endTime")String endTime, @Param("workId")Long workId);
}