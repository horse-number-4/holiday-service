package com.planit_square.holiday_service.application.inbound;

import com.planit_square.holiday_service.adapter.web.HolidayResponse;
import com.planit_square.holiday_service.adapter.web.HolidaySearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HolidayQueryUseCase {

    Page<HolidayResponse> findHolidays(HolidaySearchCondition condition, Pageable pageable);

    long count();
}
