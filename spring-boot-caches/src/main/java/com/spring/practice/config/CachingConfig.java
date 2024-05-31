package com.spring.practice.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

// @SpringBootApplication에 추가해도 되지만, 어차피 캐시 설정을 위해 config 클래스 사용한다면 여기에 추가해도 됨
@EnableCaching
@Configuration
public class CachingConfig {

    /**
     * ConcurrentMapCacheManager
     * - 스레드 안정성 제공(동시성 제어 해준다는 의미)
     * - 요청 시 자동으로 캐시 생성, 개발자가 직접 만들 필요 없음
     * - SimpleCacheManager에 비해 다양한 캐시 기능 없음, ConcurrentMap만 내부적으로 사용
     */
    @Bean // spring IoC 컨테이너에 의해 관리가 되어지는 bean 객체로 등록
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setCacheNames(List.of("member-list-caches"));
        cacheManager.setCacheNames(List.of("member-id-caches"));
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

    /**
     * SimpleCacheManager
     * - 스레드 안정성 제공 안함, 개발자가 직접 동시성 제어 필요(CacheService + 함수 레벨 synchronized 사용)
     * - 캐시(Cache) 인스턴스 미리 설정(생성)해야 함
     * - 다양한 캐시 구현체 사용 가능하도록 유연성 제공
     */
    /*@Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<>();
        caches.add(new ConcurrentMapCache("hot-trendwords-cache"));
        caches.add(new ConcurrentMapCache("deily-trendwords-cache"));
        cacheManager.setCaches(caches);
        return cacheManager;
    }*/
}