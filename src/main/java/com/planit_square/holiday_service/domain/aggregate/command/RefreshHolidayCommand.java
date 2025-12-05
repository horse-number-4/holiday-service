package com.planit_square.holiday_service.domain.aggregate.command;

public record RefreshHolidayCommand(
        int year,
        String code
) {
}
