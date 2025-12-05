package com.planit_square.holiday_service.application.outbound;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;

import java.util.List;

public interface HolidayKeeper {

    List<RegisterCountryCommand> findCountries();

    List<RegisterHolidayCommand> findHolidays(String code);

    List<RegisterHolidayCommand> findHolidays(int year, String code);
}
