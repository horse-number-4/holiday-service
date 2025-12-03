package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.CountryCommandUseCase;
import com.planit_square.holiday_service.application.outbound.CountryRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CountryCommandService implements CountryCommandUseCase {

    private final CountryRepository countryRepository;

    @Override
    public void register(List<RegisterCountryCommand> commands) {

        List<Country> countries = commands.stream()
                .map(Country::register)
                .toList();

        countryRepository.saveAll(countries);
    }
}
