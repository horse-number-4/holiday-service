package com.planit_square.holiday_service.adapter.web;

import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.domain.aggregate.command.RefreshHolidayCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/holidays")
public class HolidayApiController {

    private final HolidayCommandUseCase holidayCommandUseCase;

    @PostMapping
    public void refresh(@RequestBody RefreshHolidayRequest request) {
        RefreshHolidayCommand command = request.toCommand();
        holidayCommandUseCase.refresh(command);
    }

    @DeleteMapping("/{year}/{code}")
    public void deleteHoliday(@PathVariable int year, @PathVariable String code) {
        holidayCommandUseCase.delete(year, code);
    }
}
