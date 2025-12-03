package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.application.outbound.CountryRepository;
import com.planit_square.holiday_service.application.outbound.HolidayRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.Holiday;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class HolidayCommandService implements HolidayCommandUseCase {

    private final CountryRepository countryRepository;
    private final HolidayRepository holidayRepository;

    @Override
    public void register(String code, List<RegisterHolidayCommand> commands) {

        Country country = countryRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 국가입니다."));

        List<Holiday> holidays = commands.stream()
                .map(command -> Holiday.register(country, command))
                .toList();

        holidayRepository.saveAll(holidays);
    }
}
