package com.planit_square.holiday_service.application.outbound;

import com.planit_square.holiday_service.adapter.web.HolidayResponse;
import com.planit_square.holiday_service.adapter.web.HolidaySearchCondition;
import com.planit_square.holiday_service.domain.aggregate.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HolidayRepository {

    List<Holiday> findByYearAndCode(int year, String code);

    void bulkInsert(List<Holiday> holidays);

    void save(Holiday newHoliday);

    void deleteAll(List<Holiday> holidays);

    Page<HolidayResponse> findHolidays(HolidaySearchCondition condition, Pageable pageable);
}
