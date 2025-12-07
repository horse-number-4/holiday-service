package com.planit_square.holiday_service.adapter.persistence;

import com.planit_square.holiday_service.domain.aggregate.Holiday;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class HolidayJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void bulkInsert(List<Holiday> holidays) {

        if (holidays.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        String sql = """
            INSERT INTO holidays (code, holiday_year, holiday_date, name, local_name)
            VALUES (:code, :holidayYear, :holidayDate, :name, :localName)
            """;

        Map<String, ?>[] holidayParams = holidays.stream()
                .map(holiday -> Map.of(
                        "code", holiday.getCountry().getCode(),
                        "holidayYear", holiday.getYear(),
                        "holidayDate", holiday.getDate(),
                        "name", holiday.getName().getName(),
                        "localName", holiday.getName().getLocalName()
                ))
                .toArray(Map[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, holidayParams);
    }
}
