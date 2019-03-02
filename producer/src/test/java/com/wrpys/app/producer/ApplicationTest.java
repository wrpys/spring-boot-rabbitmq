package com.wrpys.app.producer;

import com.wrpys.app.producer.entity.Order;
import com.wrpys.app.producer.producer.OrderSender;
import com.wrpys.app.producer.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Created by Administrator on 2019/3/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private OrderSender orderSender;

    @Test
    public void testSend1() throws Exception {
        Order order = new Order("2018081800001", "测试订单00001", System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        orderSender.sendOrder(order);
    }

    @Autowired
    private OrderService orderService;

    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order("2018081800002", "测试订单00001", System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        orderService.createOrder(order);
    }

}
