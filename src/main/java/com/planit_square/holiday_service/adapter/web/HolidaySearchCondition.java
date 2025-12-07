package com.planit_square.holiday_service.adapter.web;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "공휴일 검색 조건")
public record HolidaySearchCondition(
        @Schema(description = "연도", example = "2025") Integer year,
        @Schema(description = "국가 코드", example = "KR") String code,
        @Schema(description = "검색 시작일", example = "2025-01-01") LocalDate searchFrom,
        @Schema(description = "검색 종료일", example = "2025-12-31") LocalDate searchTo
) {
}
