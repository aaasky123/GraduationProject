package com.wxj.work.mapper;

import com.wxj.work.entity.WorkFlowMark;
import com.wxj.work.entity.WorkFlowMarkExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WorkFlowMarkMapper {
    long countByExample(WorkFlowMarkExample example);

    int deleteByExample(WorkFlowMarkExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WorkFlowMark record);

    int insertSelective(WorkFlowMark record);

    List<WorkFlowMark> selectByExample(WorkFlowMarkExample example);

    WorkFlowMark selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WorkFlowMark record, @Param("example") WorkFlowMarkExample example);

    int updateByExample(@Param("record") WorkFlowMark record, @Param("example") WorkFlowMarkExample example);

    int updateByPrimaryKeySelective(WorkFlowMark record);

    int updateByPrimaryKey(WorkFlowMark record);

    /**
     * 添加批注
     */
    @Insert("insert into work_flow_mark values (0, #{workId}, #{workFlowId}, #{createUserId}, #{markContent}, #{markTime});")
    int addMark(WorkFlowMark workFlowMark);

    /**
     * 查看批注
     */
    @Select("select * from work_flow_mark where work_id=#{workId} and work_flow_id=#{workFlowId};")
    List<WorkFlowMark> queryMark(WorkFlowMark workFlowMark);
}