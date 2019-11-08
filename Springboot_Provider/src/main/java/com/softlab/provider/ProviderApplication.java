package com.softlab.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


/**
 * @author : Ar1es
 * @date : 2019/7/30
 * @since : Java 8
 */
@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
public class ProviderApplication{

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
