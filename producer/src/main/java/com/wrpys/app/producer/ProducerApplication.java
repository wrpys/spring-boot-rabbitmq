package com.wrpys.app.producer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wrp
 * @Date 2017/11/7
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.wrpys.app.producer.dao"})
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}
