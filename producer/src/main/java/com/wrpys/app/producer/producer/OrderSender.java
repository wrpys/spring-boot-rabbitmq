package com.wrpys.app.producer.producer;

import com.wrpys.app.producer.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/3/2.
 */
@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order) throws Exception {

        rabbitTemplate.convertAndSend("order-exchange", "order.abcd",
                order, new CorrelationData(order.getMessageId()));

    }

}
