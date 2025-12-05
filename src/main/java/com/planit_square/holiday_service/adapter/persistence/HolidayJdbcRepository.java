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

    private static final int BATCH_SIZE = 2000;

    public void bulkInsert(List<Holiday> holidays) {

        if (holidays.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        String sql = """
            INSERT INTO holidays (code, holiday_year, holiday_date, name, local_name)
            VALUES (:code, :holidayYear, :holidayDate, :name, :localName)
            """;

        for (int i = 0; i < holidays.size(); i += BATCH_SIZE) {
            List<Holiday> chunk = holidays.subList(i, Math.min(i + BATCH_SIZE, holidays.size()));

            Map<String, ?>[] batchParams = chunk.stream()
                    .map(holiday -> Map.of(
                            "code", holiday.getCountry().getCode(),
                            "holidayYear", holiday.getYear(),
                            "holidayDate", holiday.getDate(),
                            "name", holiday.getName().name(),
                            "localName", holiday.getName().localName()
                    ))
                    .toArray(Map[]::new);

            namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
        }
    }
}
