package com.planit_square.holiday_service.adapter.cli;

import com.planit_square.holiday_service.application.HolidayLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final HolidayLoader holidayLoader;

    @Override
    public void run(String... args) throws Exception {
        holidayLoader.initialize();
    }
}
