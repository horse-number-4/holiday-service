package com.planit_square.holiday_service.domain.aggregate;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import com.planit_square.holiday_service.domain.valueobject.HolidayName;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Holiday {

    private Long id;
    private Country country;
    private Integer year;
    private LocalDate date;
    private HolidayName name;

    public static Holiday register(Country country, RegisterHolidayCommand command) {

        Holiday holiday = new Holiday();

        holiday.country = country;
        holiday.year = command.year();
        holiday.date = command.date();
        holiday.name = new HolidayName(command.name(), command.localName());

        return holiday;
    }
}
