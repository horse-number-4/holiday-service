package com.planit_square.holiday_service.application.outbound;

import com.planit_square.holiday_service.domain.aggregate.Holiday;

import java.util.List;

public interface HolidayRepository {

    void saveAll(List<Holiday> holidays);
}
