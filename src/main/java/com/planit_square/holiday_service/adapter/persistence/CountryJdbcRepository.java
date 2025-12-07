package com.planit_square.holiday_service.adapter.persistence;

import com.fasterxml.jackson.databind.util.Named;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.Holiday;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class CountryJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final int BATCH_SIZE = 200;

    public void bulkInsert(List<Country> countries) {

        if (countries.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        String sql = """
            INSERT INTO countries (code, name, is_holiday_loaded)
            VALUES (:code, :name, :isHolidayLoaded)
            """;

        for (int i = 0; i < countries.size(); i += BATCH_SIZE) {
            List<Country> chunk = countries.subList(i, Math.min(i + BATCH_SIZE, countries.size()));

            Map<String, ?>[] batchParams = chunk.stream()
                    .map(country -> Map.of(
                            "code", country.getCode(),
                            "name", country.getName(),
                            "isHolidayLoaded", country.isHolidayLoaded()
                    ))
                    .toArray(Map[]::new);

            namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
        }
    }
}
