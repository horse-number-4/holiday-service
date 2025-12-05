package com.planit_square.holiday_service.adapter.persistence;

import com.planit_square.holiday_service.application.outbound.HolidayRepository;
import com.planit_square.holiday_service.domain.aggregate.Holiday;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class HolidayPersistenceAdapter implements HolidayRepository {

    private final HolidayJpaRepository holidayJpaRepository;
    private final HolidayJdbcRepository holidayJdbcRepository;

    @Override
    public List<Holiday> findByYearAndCode(int year, String code) {
        return holidayJpaRepository.findByYearAndCode(year, code);
    }

    @Override
    public void bulkInsert(List<Holiday> holidays) {
        holidayJdbcRepository.bulkInsert(holidays);
    }

    @Override
    public void save(Holiday newHoliday) {
        holidayJpaRepository.save(newHoliday);
    }

    @Override
    public void deleteAll(List<Holiday> holidays) {
        holidayJpaRepository.deleteAll(holidays);
    }
}
