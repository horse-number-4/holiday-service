package com.planit_square.holiday_service.adapter.cli;

import com.planit_square.holiday_service.application.HolidayInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final HolidayInitializer holidayInitializer;

    @Override
    public void run(String... args) throws Exception {
        log.info("### 초기 데이터 로드");
        holidayInitializer.initialize();
    }
}
