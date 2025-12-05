package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.application.outbound.CountryRepository;
import com.planit_square.holiday_service.application.outbound.HolidayKeeper;
import com.planit_square.holiday_service.application.outbound.HolidayRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.Holiday;
import com.planit_square.holiday_service.domain.aggregate.command.RefreshHolidayCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class HolidayCommandService implements HolidayCommandUseCase {

    private final CountryRepository countryRepository;
    private final HolidayRepository holidayRepository;
    private final HolidayKeeper holidayKeeper;

    @Override
    public void initialize(List<RegisterHolidayCommand> commands) {

        Set<String> codes = toCountryCodes(commands);
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
    public void refresh(RefreshHolidayCommand command) {

        Country country = countryRepository.findByCode(command.code())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 국가입니다."));

        List<Holiday> holidays = holidayRepository.findByYearAndCode(command.year(), command.code());
        Map<LocalDate, Holiday> holidayMap = toHolidayMap(holidays);

        List<RegisterHolidayCommand> holidayCommands = holidayKeeper.findHolidays(command.year(), command.code());

        for (RegisterHolidayCommand holidayCommand : holidayCommands) {

            Holiday holiday = holidayMap.get(holidayCommand.date());

            if (Objects.nonNull(holiday)) {
                if (holiday.isDifferent(holidayCommand)) {
                    holiday.update(holidayCommand);
                }
            } else {
                Holiday newHoliday = Holiday.register(country, holidayCommand);
                holidayRepository.save(newHoliday);
            }
        }
    }

    @Override
    public void delete(int year, String code) {
        List<Holiday> holidays = holidayRepository.findByYearAndCode(year, code);
        holidayRepository.deleteAll(holidays);
    }

    private Set<String> toCountryCodes(List<RegisterHolidayCommand> commands) {
        return commands.stream()
                .map(RegisterHolidayCommand::code)
                .collect(Collectors.toSet());
    }

    private Map<LocalDate, Holiday> toHolidayMap(List<Holiday> holidays) {
        return holidays.stream()
                .collect(Collectors.toMap(Holiday::getDate, Function.identity()));
    }
}
