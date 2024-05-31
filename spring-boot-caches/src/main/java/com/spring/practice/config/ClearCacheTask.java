package com.spring.practice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @Component
 * - 개발자가 직접 생성한 클래스를 스프링 IoC 컨테이너에 bean 객체로 등록
 * - 해당 어노테이션이 있어야 ComponentScan 대상이 되어서 bean으로 등록됨
 * - @Component, @Controller, @Service, @Repository 해당 어노테이션들은 @Component를 상속받아 만들어진 것
 * - 또한 해당 어노테이션이 있어야 @Autowired를 통해 의존성 주입이 가능
 */
@Slf4j
@Component
@EnableScheduling // 스케쥴링 가능
public class ClearCacheTask {
    //TODO: @Scheduled 어노테이션 사용하여 주기적으로 캐시를 삭제하는 작업 수행 필요
}