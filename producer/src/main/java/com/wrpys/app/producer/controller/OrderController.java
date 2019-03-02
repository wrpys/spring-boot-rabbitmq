package com.wrpys.app.producer.controller;

import com.alibaba.fastjson.JSON;
import com.wrpys.app.producer.entity.Order;
import com.wrpys.app.producer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Administrator on 2017/11/7.
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("create")
    public String create() throws Exception {
        Order order = new Order("2018081800010", "测试订单00001", System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        orderService.createOrder(order);
        return JSON.toJSONString(order);
    }

}
