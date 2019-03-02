package com.wrpys.app.consumer.consumer;

import com.rabbitmq.client.Channel;
import com.wrpys.app.producer.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2019/3/2.
 */
@Component
public class OrderReceiver {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                                    durable = "${spring.rabbitmq.listener.order.queue.durable}"),
                    exchange = @Exchange(name = "${spring.rabbitmq.listener.order.exchange.name}",
                                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                                    type = "${spring.rabbitmq.listener.order.exchange.type}"),
                    key = "${spring.rabbitmq.listener.order.key}"
            )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, @Headers Map<String, Object> headers,
                               Channel channel) throws Exception {
        System.out.println("-------------------收到消息，开始消费------------------------");
        System.out.println("订单ID：" + order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);

    }

}
