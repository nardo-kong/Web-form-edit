package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // 获取环境变量SPRING_DATASOURCE_PASSWORD的值
        String datasourcePassword = System.getenv("SPRING_DATASOURCE_PASSWORD");
        
        // 打印环境变量的值，如果未设置则打印null
        System.out.println("SPRING_DATASOURCE_PASSWORD: " + datasourcePassword);

        SpringApplication.run(DemoApplication.class, args);
    }

}
