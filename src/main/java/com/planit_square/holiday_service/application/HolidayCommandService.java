package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.application.outbound.CountryRepository;
import com.planit_square.holiday_service.application.outbound.HolidayRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.Holiday;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class HolidayCommandService implements HolidayCommandUseCase {

    private final CountryRepository countryRepository;
    private final HolidayRepository holidayRepository;

    @Override
    public void initialize(List<RegisterHolidayCommand> commands) {

        Set<String> codes = toCodes(commands);
        List<Country> countries = countryRepository.findAllByCodeIn(codes);

        Map<String, Country> countryMap = countries.stream()
                .collect(Collectors.toMap(Country::getCode, Function.identity()));

        List<Holiday> holidays = commands.stream()
                .map(command -> {
                    Country country = countryMap.get(command.code());
                    return Holiday.register(country, command);
                })
                .toList();

        holidayRepository.bulkInsert(holidays);
    }

    @Override
    public void delete(int year, String code) {
        List<Holiday> holidays = holidayRepository.findByYearAndCode(year, code);
        holidayRepository.deleteAll(holidays);
    }

    private Set<String> toCodes(List<RegisterHolidayCommand> commands) {
        return commands.stream()
                .map(RegisterHolidayCommand::code)
                .collect(Collectors.toSet());
    }
}
