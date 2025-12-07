package com.planit_square.holiday_service.adapter.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Schema(description = "공휴일 검색 조회 응답 DTO")
@Getter
public class HolidayResponse {

    @Schema(description = "공휴일 ID", example = "1")
    private final Long id;

    @Schema(description = "공휴일명", example = "Lunar New Year")
    private final String name;

    @Schema(description = "공휴일 지역명", example = "설날")
    private final String localName;

    @Schema(description = "공휴일", example = "2025-01-01")
    private final LocalDate date;

    @Schema(description = "공휴일 연도", example = "2025")
    private final int year;

    private final CountryResponse country;

    public HolidayResponse(
        Long id,
        String name,
        String localName,
        LocalDate date,
        int year,
        CountryResponse country
    ) {
        this.id = id;
        this.name = name;
        this.localName = localName;
        this.date = date;
        this.year = year;
        this.country = country;
    }

    @Schema(description = "국가 검색 조회 응답 DTO")
    @Getter
    public static class CountryResponse {

        @Schema(description = "국가 코드", example = "KR")
        private final String code;

        @Schema(description = "국가명", example = "South Korea")
        private final String name;

        public CountryResponse(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
