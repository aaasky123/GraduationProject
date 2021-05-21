package com.wxj.work.mapper;

import com.wxj.work.entity.File;
import com.wxj.work.entity.FileExample;
import java.util.List;

import com.wxj.work.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    long countByExample(FileExample example);

    int deleteByExample(FileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(File record);

    int insertSelective(File record);

    List<File> selectByExample(FileExample example);

    File selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") File record, @Param("example") FileExample example);

    int updateByExample(@Param("record") File record, @Param("example") FileExample example);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);

    /**
     * 上传头像
     */
    @Update("update user set picture=#{picture} where number=#{number};")
    int uploadPhoto(User user);

    /**
     * 上传文件，向数据库插入信息
     */
    @Insert("insert into file values(0,#{fileName}, #{newFileName}, #{path}, #{workId}, #{workFlowId});")
    int uploadFile(File file);

    /**
     * 查询文件
     */
    @Select("select * from file where work_id=#{workId} and work_flow_id=#{workFlowId};")
    List<File> queryFile(File file);

    /**
     * 查询路径
     */
    @Select("select * from file where new_file_name=#{newFileName}; ")
    File queryPath(String newFileName);
}