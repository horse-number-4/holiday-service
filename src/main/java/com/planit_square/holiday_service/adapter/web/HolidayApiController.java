package com.planit_square.holiday_service.adapter.web;

import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/holidays")
public class HolidayApiController {

    private final HolidayCommandUseCase holidayCommandUseCase;

    @DeleteMapping("/{code}/{year}")
    public void deleteHoliday(@PathVariable String code, @PathVariable int year) {
        holidayCommandUseCase.delete(code, year);
    }
}
