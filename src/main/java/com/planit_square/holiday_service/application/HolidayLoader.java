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
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class HolidayLoader {

    private final HolidayKeeper holidayKeeper;
    private final CountryCommandUseCase countryCommandUseCase;
    private final HolidayCommandUseCase holidayCommandUseCase;

    public void initialize() {

        Instant start = Instant.now();

        log.info("### 초기화 시작 ###");

        List<RegisterCountryCommand> countryCommands = holidayKeeper.findCountries();
        countryCommandUseCase.initialize(countryCommands);

        // TODO: 고민 필요 -> 병렬 처리
        List<RegisterHolidayCommand> allHolidayCommands = new ArrayList<>();

        for (RegisterCountryCommand countryCommand : countryCommands) {
            List<RegisterHolidayCommand> holidayCommands = holidayKeeper.findHolidays(countryCommand.code());
            allHolidayCommands.addAll(holidayCommands);
        }

        holidayCommandUseCase.initialize(allHolidayCommands);

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        log.info("### 초기화 종료 ###");
        log.info("### 소요 시간: {}분 {}초 (총 {}개 공휴일)",
                duration.toMinutes(),
                duration.toSecondsPart(),
                allHolidayCommands.size()
        );

    }
}
