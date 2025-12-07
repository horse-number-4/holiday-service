package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.CountryCommandUseCase;
import com.planit_square.holiday_service.application.outbound.CountryRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CountryCommandService implements CountryCommandUseCase {

    private final CountryRepository countryRepository;

    @Override
    public void initialize(List<RegisterCountryCommand> commands) {

        List<Country> countries = commands.stream()
                .map(Country::register)
                .toList();

        countryRepository.bulkInsert(countries);
    }

    @Override
    public void refresh(List<RegisterCountryCommand> commands) {

        List<Country> countries = countryRepository.findAll();

        Set<String> codes = countries.stream()
                .map(Country::getCode)
                .collect(Collectors.toSet());

        List<Country> newCountries = commands.stream()
                .filter(command -> !codes.contains(command.code()))
                .map(Country::register)
                .toList();

        if (!newCountries.isEmpty()) {
            countryRepository.bulkInsert(newCountries);
        }
    }

    @Override
    public void updateIsHolidayLoaded(Set<String> codes) {
        List<Country> countries = countryRepository.findAllByCodeIn(codes);
        for (Country country : countries) {
            country.updateLoadFail();
        }
    }
}
