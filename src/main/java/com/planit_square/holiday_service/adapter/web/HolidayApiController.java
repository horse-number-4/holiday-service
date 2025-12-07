package com.planit_square.holiday_service.adapter.web;

import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.application.inbound.HolidayQueryUseCase;
import com.planit_square.holiday_service.domain.aggregate.command.RefreshHolidayCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Holidays", description = "공휴일 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/holidays")
public class HolidayApiController {

    private final HolidayQueryUseCase holidayQueryUseCase;
    private final HolidayCommandUseCase holidayCommandUseCase;

    @Operation(summary = "공휴일 검색 조회", description = "공휴일을 검색 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<HolidayResponse>> findHolidays(HolidaySearchCondition condition, @Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(holidayQueryUseCase.findHolidays(condition, pageable));
    }

    @Operation(summary = "공휴일 재동기화", description = "해당 연도와 국가의 공휴일 재동기화합니다.")
    @PostMapping
    public ResponseEntity<Void> refresh(@RequestBody RefreshHolidayRequest request) {
        RefreshHolidayCommand command = request.toCommand();
        holidayCommandUseCase.refresh(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "공휴일 삭제", description = "해당 연도와 국가의 공휴일을 삭제합니다.")
    @DeleteMapping("/{year}/{code}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable int year, @PathVariable String code) {
        holidayCommandUseCase.delete(year, code);
        return ResponseEntity.noContent().build();
    }
}
