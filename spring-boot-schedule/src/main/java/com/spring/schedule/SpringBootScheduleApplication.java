package com.spring.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케쥴링 기능 활성화
@SpringBootApplication
public class SpringBootScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootScheduleApplication.class, args);
    }
}