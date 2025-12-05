package com.planit_square.holiday_service.adapter.persistence;

import com.planit_square.holiday_service.application.outbound.CountryRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class CountryPersistenceAdapter implements CountryRepository {

    private final CountryJpaRepository countryJpaRepository;
    private final CountryJdbcRepository countryJdbcRepository;

    @Override
    public void bulkInsert(List<Country> countries) {
        countryJdbcRepository.bulkInsert(countries);
    }

    @Override
    public List<Country> findAllByCodeIn(Set<String> codes) {
        return countryJpaRepository.findAllByCodeIn(codes);
    }

    @Override
    public Optional<Country> findByCode(String code) {
        return countryJpaRepository.findByCode(code);
    }
}
