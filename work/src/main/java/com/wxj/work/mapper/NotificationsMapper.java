package com.wxj.work.mapper;

import com.wxj.work.entity.Notifications;
import com.wxj.work.entity.NotificationsExample;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotificationsMapper {
    long countByExample(NotificationsExample example);

    int deleteByExample(NotificationsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Notifications record);

    int insertSelective(Notifications record);

    List<Notifications> selectByExample(NotificationsExample example);

    Notifications selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Notifications record, @Param("example") NotificationsExample example);

    int updateByExample(@Param("record") Notifications record, @Param("example") NotificationsExample example);

    int updateByPrimaryKeySelective(Notifications record);

    int updateByPrimaryKey(Notifications record);

    /**
     * 查询通知
     * @param userIdOfQuery
     * @return
     */
    List<Notifications> queryNotifications(Long userIdOfQuery);

    /**
     * 添加通知
     */
    @Insert("insert into notifications values (0, #{createUserId}, #{notificationsContent}, #{createTime});")
    int addNotifications(Notifications notifications);
}