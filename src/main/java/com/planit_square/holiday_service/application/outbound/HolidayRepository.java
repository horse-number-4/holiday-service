package com.planit_square.holiday_service.application.outbound;

import com.planit_square.holiday_service.domain.aggregate.Holiday;

import java.util.List;

public interface HolidayRepository {

    void saveAll(List<Holiday> holidays);

    List<Holiday> findByYearAndCode(int year, String code);

    void bulkInsert(List<Holiday> holidays);

    void deleteAll(List<Holiday> holidays);
}
