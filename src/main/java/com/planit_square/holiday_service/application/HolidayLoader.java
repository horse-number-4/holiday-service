package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.CountryCommandUseCase;
import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.application.outbound.HolidayKeeper;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class HolidayLoader {

    private final HolidayKeeper holidayKeeper;
    private final CountryCommandUseCase countryCommandUseCase;
    private final HolidayCommandUseCase holidayCommandUseCase;

    public void initialize() {

        // TODO: 고민 필요 -> 코드로 조회 시
        List<RegisterCountryCommand> countryCommands = holidayKeeper.findCountries();
        countryCommandUseCase.register(countryCommands);

        for (RegisterCountryCommand countryCommand : countryCommands) {
            List<RegisterHolidayCommand> holidayCommands = holidayKeeper.findHolidays(countryCommand.code());
            holidayCommandUseCase.register(countryCommand.code(), holidayCommands);
        }
    }
}
