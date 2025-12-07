package com.planit_square.holiday_service.domain.aggregate.command;

public record RefreshHolidayCommand(
        int year,
        String code
) {
    public static RefreshHolidayCommand of(int year, String code) {
        return new RefreshHolidayCommand(year, code);
    }
}
