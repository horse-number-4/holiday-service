package com.planit_square.holiday_service.adapter.persistence;

import com.planit_square.holiday_service.application.outbound.CountryRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CountryPersistenceAdapter implements CountryRepository {

    private final CountryJpaRepository countryJpaRepository;

    @Override
    public Optional<Country> findByCode(String code) {
        return countryJpaRepository.findByCode(code);
    }

    @Override
    public void saveAll(List<Country> countries) {
        countryJpaRepository.saveAll(countries);
    }
}
