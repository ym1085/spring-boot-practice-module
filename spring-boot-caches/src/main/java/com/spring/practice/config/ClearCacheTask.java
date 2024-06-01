package com.spring.practice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Component
 * - 개발자가 직접 생성한 클래스를 스프링 IoC 컨테이너에 bean 객체로 등록
 * - 해당 어노테이션이 있어야 ComponentScan 대상이 되어서 bean으로 등록됨
 * - @Component, @Controller, @Service, @Repository 해당 어노테이션들은 @Component를 상속받아 만들어진 것
 * - 또한 해당 어노테이션이 있어야 @Autowired를 통해 의존성 주입이 가능
 *
 * @Scheduled
 * - 스프링에서 제공하는 스케쥴링 어노테이션
 *
 * - 1 2 3 4 5 6 // 순서
 * - * * * * * * // 실행주기 문자열
 * - 초(0-59)
 * - 분(0-59)
 * - 시(0-23)
 * - 일(1-31)
 * - 월(1-12)
 * - 요일(0-7)
 */
@Slf4j
@RequiredArgsConstructor
@Component
@EnableScheduling // 스케쥴링 가능
public class ClearCacheTask {
    private final CacheManager cacheManager;

    // 매 1분마다 캐시 초기화 -> 테스트용
    // https://emunhi.com/view/201807/30140115253
    @Scheduled(cron = "0 * * * * ?") // 초, 분, 시, 일, 월, 요일
    public void evictMemberId10Min() {
        cacheManager.getCacheNames().forEach(name -> {
            if (StringUtils.startsWith(name, "member-id-")) {
                log.info("Cache clear => {}", name);
                Objects.requireNonNull(cacheManager.getCache(name)).clear();
            }
        });
    }
}