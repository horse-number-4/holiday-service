package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.adapter.web.HolidayResponse;
import com.planit_square.holiday_service.adapter.web.HolidaySearchCondition;
import com.planit_square.holiday_service.application.inbound.HolidayQueryUseCase;
import com.planit_square.holiday_service.application.outbound.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HolidayQueryService implements HolidayQueryUseCase {

    private final HolidayRepository holidayRepository;

    @Override
    public Page<HolidayResponse> findHolidays(HolidaySearchCondition condition, Pageable pageable) {
        return holidayRepository.findHolidays(condition, pageable);
    }

    @Override
    public long count() {
        return holidayRepository.count();
    }
}
