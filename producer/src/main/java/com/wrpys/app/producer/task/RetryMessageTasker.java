package com.wrpys.app.producer.task;

import com.alibaba.fastjson.JSON;
import com.wrpys.app.producer.constant.Constants;
import com.wrpys.app.producer.dao.BrokerMessageLogDao;
import com.wrpys.app.producer.entity.BrokerMessageLog;
import com.wrpys.app.producer.entity.Order;
import com.wrpys.app.producer.producer.RabbitOrderSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/3/2.
 */
@Component
public class RetryMessageTasker {

    @Autowired
    private RabbitOrderSender rabbitOrderSender;
    @Autowired
    private BrokerMessageLogDao brokerMessageLogDao;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void reSend() {
        System.out.println("-----------定时任务开始------------");
        List<BrokerMessageLog> list = brokerMessageLogDao.query4StatusAndTimeoutMessage();
        list.forEach(v -> {
            if (v.getTryCount() >= 3) {
                brokerMessageLogDao.changeBrokerMessageLogStatus(v.getMessageId(), Constants.ORDER_SEND_FAILURE, new Date());
            } else {
                brokerMessageLogDao.update4ReSend(v.getMessageId(), new Date());
                Order reSendOrder = JSON.parseObject(v.getMessage(), Order.class);
                try {
                    rabbitOrderSender.sendOrder(reSendOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("-----------异常处理----------");
                }
            }
        });
    }

}
