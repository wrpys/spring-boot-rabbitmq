package com.wrpys.app.producer.producer;

import com.wrpys.app.producer.constant.Constants;
import com.wrpys.app.producer.dao.BrokerMessageLogDao;
import com.wrpys.app.producer.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2019/3/2.
 */
@Component
public class RabbitOrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogDao brokerMessageLogDao;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.out.println("correlationData:" + correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                brokerMessageLogDao.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
            } else {
                System.out.println("异常处理...");
            }
        }
    };

    public void sendOrder(Order order) throws Exception {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.convertAndSend("order-exchange", "order.ABC",
                order, new CorrelationData(order.getMessageId()));

    }

}
