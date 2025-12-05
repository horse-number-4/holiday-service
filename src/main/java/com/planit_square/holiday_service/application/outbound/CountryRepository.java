package com.planit_square.holiday_service.application.outbound;

import com.planit_square.holiday_service.domain.aggregate.Country;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CountryRepository {

    Optional<Country> findByCode(String code);

    void saveAll(List<Country> countries);

    List<Country> findAllByCodeIn(Set<String> codes);
}
