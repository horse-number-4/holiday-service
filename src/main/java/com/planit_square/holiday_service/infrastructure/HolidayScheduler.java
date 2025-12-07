package com.planit_square.holiday_service.infrastructure;

import com.planit_square.holiday_service.application.inbound.CountryCommandUseCase;
import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.application.outbound.HolidayKeeper;
import com.planit_square.holiday_service.domain.aggregate.command.RefreshHolidayCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class HolidayScheduler {

    private final HolidayKeeper holidayKeeper;
    private final CountryCommandUseCase countryCommandUseCase;
    private final HolidayCommandUseCase holidayCommandUseCase;

    @Scheduled(cron = "0 0 1 2 1 *", zone = "Asia/Seoul")
    public void synchronizeHolidays() {

        List<RegisterCountryCommand> countryCommands = holidayKeeper.findCountries();
        countryCommandUseCase.refresh(countryCommands);
        
        int currentYear = LocalDate.now().getYear();
        int previousYear = currentYear - 1;

        syncHolidays(currentYear, countryCommands);
        syncHolidays(previousYear, countryCommands);
    }

    private void syncHolidays(int year, List<RegisterCountryCommand> countryCommands) {
        for (RegisterCountryCommand countryCommand : countryCommands) {
            RefreshHolidayCommand command = RefreshHolidayCommand.of(year, countryCommand.code());
            holidayCommandUseCase.refresh(command);
        }
    }
}
