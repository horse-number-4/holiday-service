package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.outbound.HolidayRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.Holiday;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class HolidayBatchHelper {

    private final HolidayRepository holidayRepository;

    @Transactional
    public void saveBatch(List<RegisterHolidayCommand> batch, Map<String, Country> countryMap) {

        List<Holiday> holidays = batch.stream()
                .map(command -> {
                    Country country = countryMap.get(command.code());
                    return Holiday.register(country, command);
                })
                .toList();

        if (!holidays.isEmpty()) {
            holidayRepository.bulkInsert(holidays);
        }
    }
}
