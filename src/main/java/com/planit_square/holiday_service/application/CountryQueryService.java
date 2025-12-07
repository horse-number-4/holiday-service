package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.CountryQueryUseCase;
import com.planit_square.holiday_service.application.outbound.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CountryQueryService implements CountryQueryUseCase {

    private final CountryRepository countryRepository;

    @Override
    public long count() {
        return countryRepository.count();
    }
}
