package com.wrpys.app.producer.service;

import com.alibaba.fastjson.JSON;
import com.wrpys.app.producer.constant.Constants;
import com.wrpys.app.producer.dao.BrokerMessageLogDao;
import com.wrpys.app.producer.dao.OrderDao;
import com.wrpys.app.producer.entity.BrokerMessageLog;
import com.wrpys.app.producer.entity.Order;
import com.wrpys.app.producer.producer.RabbitOrderSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2019/3/2.
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BrokerMessageLogDao brokerMessageLogDao;
    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    public void createOrder(Order order) throws Exception {
        Date orderTime = new Date();
        orderDao.insert(order);
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(order.getMessageId());
        brokerMessageLog.setMessage(JSON.toJSONString(order));
        brokerMessageLog.setStatus(Constants.ORDER_SENDING);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderTime);
        calendar.add(Calendar.MINUTE, Constants.ORDER_TIMEOUT);
        brokerMessageLog.setNextRetry(calendar.getTime());
        brokerMessageLog.setCreateTime(orderTime);
        brokerMessageLog.setUpdateTime(orderTime);
        brokerMessageLogDao.insert(brokerMessageLog);
        rabbitOrderSender.sendOrder(order);
    }

}
