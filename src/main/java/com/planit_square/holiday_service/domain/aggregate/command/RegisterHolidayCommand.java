package com.planit_square.holiday_service.domain.aggregate.command;

import java.time.LocalDate;

public record RegisterHolidayCommand(
        String code,
        Integer year,
        LocalDate date,
        String name,
        String localName
) {}
