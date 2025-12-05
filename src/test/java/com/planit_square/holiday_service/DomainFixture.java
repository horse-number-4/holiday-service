package com.planit_square.holiday_service;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;

import java.time.LocalDate;

public class DomainFixture {

    public static RegisterCountryCommand createRegisterCountryCommand() {
        return new RegisterCountryCommand("KR", "South Korea");
    }

    public static RegisterHolidayCommand createRegisterHolidayCommand() {
        return new RegisterHolidayCommand("KR", 2025, LocalDate.of(2025, 12, 25), "Christmas", "크리스마스");
    }
}
