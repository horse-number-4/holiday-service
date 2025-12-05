package com.planit_square.holiday_service.adapter.client;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;

import java.time.LocalDate;
import java.util.List;

public record NagerHolidayResponseDTO(
        String date,
        String localName,
        String name,
        String countryCode,
        String fixed,
        String global,
        List<String> counties,
        String launchYear,
        List<String> types
) {
    public RegisterHolidayCommand toCommand() {
        return new RegisterHolidayCommand(LocalDate.parse(date).getYear(), LocalDate.parse(date), name, localName);
    }
}
