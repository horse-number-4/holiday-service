package com.planit_square.holiday_service.adapter.web;

import com.planit_square.holiday_service.domain.aggregate.command.RefreshHolidayCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공휴일 재동기화 요청 DTO")
public record RefreshHolidayRequest(
        @Schema(description = "연도", example = "2025") int year,
        @Schema(description = "국가 코드", example = "KR") String code
) {
    public RefreshHolidayCommand toCommand() {
        return new RefreshHolidayCommand(year, code);
    }
}
