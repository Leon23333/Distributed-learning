package com.xu.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication(scanBasePackages="com.xu")
@EnableDubboConfiguration
public class ProviderApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
