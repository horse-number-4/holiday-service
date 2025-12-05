package com.planit_square.holiday_service.adapter.web;

import com.planit_square.holiday_service.domain.aggregate.command.RefreshHolidayCommand;

public record RefreshHolidayRequest(
        int year,
        String code
) {
    public RefreshHolidayCommand toCommand() {
        return new RefreshHolidayCommand(year, code);
    }
}
