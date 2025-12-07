package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.CountryCommandUseCase;
import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.application.outbound.HolidayKeeper;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class HolidayLoader {

    private final HolidayKeeper holidayKeeper;
    private final CountryCommandUseCase countryCommandUseCase;
    private final HolidayCommandUseCase holidayCommandUseCase;

    private static final int THREAD_POOL = 5;

    public void initialize() {

        Instant start = Instant.now();
        log.info("### 초기화 시작 ###");

        // 국가 초기 데이터 적재
        List<RegisterCountryCommand> countryCommands = initializeCountries();

        // TODO: 고민 필요 -> 예외처리, 재시도
        // 공휴일 초기 데이터 적재
        initializeHolidays(countryCommands);

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        log.info("### 초기화 완료 - 소요 시간: {}분 {}초", duration.toMinutes(), duration.toSecondsPart());
    }

    private List<RegisterCountryCommand> initializeCountries() {
        List<RegisterCountryCommand> countryCommands = holidayKeeper.findCountries();
        countryCommandUseCase.initialize(countryCommands);
        log.info("### 전체 국가 수: {} 건", countryCommands.size());
        return countryCommands;
    }

    private void initializeHolidays(List<RegisterCountryCommand> countryCommands) {

        List<RegisterHolidayCommand> allHolidayCommands = Collections.synchronizedList(new ArrayList<>());

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);   // 5

        try {
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (RegisterCountryCommand countryCommand : countryCommands) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        List<RegisterHolidayCommand> holidayCommands = holidayKeeper.findHolidays(countryCommand.code());
                        allHolidayCommands.addAll(holidayCommands);
                        successCount.incrementAndGet();
                        log.info("### 국가코드: {} - 성공 수: {} / 전체 국가 수: {}", countryCommand.code(), successCount.get(), countryCommands.size());
                    } catch (Exception e) {
                        failCount.incrementAndGet();
                        log.info("### 국가코드: {} - 실패 수: {} / 전체 국가 수: {}", countryCommand.code(), failCount.get(), countryCommands.size());
                    }
                }, executor);

                futures.add(future);
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            if (!allHolidayCommands.isEmpty()) {
                holidayCommandUseCase.initialize(allHolidayCommands);
            }
        } finally {
            executor.shutdown();
        }
    }
}
