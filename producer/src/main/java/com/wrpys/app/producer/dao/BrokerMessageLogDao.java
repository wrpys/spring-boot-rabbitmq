package com.wrpys.app.producer.dao;

import com.wrpys.app.producer.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BrokerMessageLogDao {
    int deleteByPrimaryKey(String messageId);

    int insert(BrokerMessageLog record);

    int insertSelective(BrokerMessageLog record);

    BrokerMessageLog selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(BrokerMessageLog record);

    int updateByPrimaryKey(BrokerMessageLog record);

    void changeBrokerMessageLogStatus(@Param(value = "messageId") String messageId, @Param(value = "status") String status, @Param(value = "updateTime") Date updateTime);

    List<BrokerMessageLog> query4StatusAndTimeoutMessage();

    void update4ReSend(@Param(value = "messageId") String messageId, @Param(value = "updateTime") Date updateTime);
}