package com.planit_square.holiday_service.adapter.web;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class HolidayResponse {

    private Long id;
    private String name;
    private String localName;
    private LocalDate date;
    private int year;
    private CountryResponse country;

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

    @Getter
    public static class CountryResponse {

        private String code;
        private String name;

        public CountryResponse(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
