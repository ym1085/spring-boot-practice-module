package com.spring.schedule.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerConfig {

    /**
     * @Scheduled
     * - fixedDelay: 이전 작업이 종료된 후 설정 시간만큼 기다린 후 시작 (ms)
     * - fixedRate: 이전 작업이 종료되지 않아도 설정 시간만큼 기다린 후 시작 (ms)
     * - initialDelay: 스케쥴러 시작 후 최초 실행 지연 시간 (ms)
     * - cron: cron 표현식
     * - zone: cron 표현식을 실행할 때 사용할 TimeZone
     */

    // 2초마다 실행, 작업이 끝나야 실행 됨
    @Scheduled(fixedDelay = 1000)
    public void performTaskByFixedDelay() throws InterruptedException {
        StopWatch stopWatch = StopWatch.createStarted();
        Thread.sleep(2000);
        stopWatch.stop();
        log.info("[001] Scheduled Task Executed at performTaskFixedDelay = {}", stopWatch.getTime(TimeUnit.MILLISECONDS) + '\n');
    }

    // 3초마다 실행, 작업이 끝나야 실행
    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void performTaskByFixedDelayInitialDelay() throws InterruptedException {
        StopWatch stopWatch = StopWatch.createStarted();
        Thread.sleep(2000);
        stopWatch.stop();
        log.info("[002] Scheduled Task Executed at performTaskByFixedDelayInitialDelay = {}", stopWatch.getTime(TimeUnit.MILLISECONDS) + '\n');
    }

    // 2초마다 무조건 실행, 작업이 끝나지 않아도 실행 됨
    @Scheduled(fixedRate = 1000)
    public void performTaskByFixedRate() throws InterruptedException {
        StopWatch stopWatch = StopWatch.createStarted();
        Thread.sleep(2000);
        stopWatch.stop();
        log.info("[003] Scheduled Task Executed at performTaskByFixedRate = {}", stopWatch.getTime(TimeUnit.MILLISECONDS) + '\n');
    }

    // 3초 기다렸다가 2초마다 실행, 작업이 끝나지 않아도 실행 됨
    @Scheduled(fixedRate = 1000, initialDelay = 1000)
    public void performTaskByFixedRateInitialDelay() throws InterruptedException {
        StopWatch stopWatch = StopWatch.createStarted();
        Thread.sleep(1000);
        stopWatch.stop();
        log.info("[004] Scheduled Task Executed at performTaskByInitialDelay = {}", stopWatch.getTime(TimeUnit.MILLISECONDS) + '\n');
    }

    // crontab 사용
    @Scheduled(cron = "10 * * * * *") // 10초마다 동작
    public void performTaskByCron() throws InterruptedException {
        StopWatch stopWatch = StopWatch.createStarted();
        Thread.sleep(1000);
        stopWatch.stop();
        log.info("[005] Scheduled Task Executed at performTaskByCron = {}", stopWatch.getTime(TimeUnit.MILLISECONDS) + '\n');
    }
}