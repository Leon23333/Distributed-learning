package com.xu.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication(scanBasePackages="com.xu")
@EnableDubboConfiguration
public class ConsumerApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
