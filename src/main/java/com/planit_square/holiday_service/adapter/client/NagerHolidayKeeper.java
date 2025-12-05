package com.planit_square.holiday_service.adapter.client;

import com.planit_square.holiday_service.application.outbound.HolidayKeeper;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NagerHolidayKeeper implements HolidayKeeper {

    private final NagerFeignClient nagerFeignClient;

    @Override
    public List<RegisterCountryCommand> findCountries() {

        // TODO: Exception 처리
        List<NagerCountryResponseDTO> countryResponses = nagerFeignClient.findCountries();
        return countryResponses.stream()
                .map(NagerCountryResponseDTO::toCommand)
                .toList();
    }

    @Override
    public List<RegisterHolidayCommand> findHolidays(String code) {

        List<RegisterHolidayCommand> holidayCommands = new ArrayList<>();

        int startedYear = LocalDateTime.now().minusYears(5).getYear();
        int endedYear = LocalDateTime.now().getYear();

        for (int year = startedYear; year <= endedYear; year++) {
            List<NagerHolidayResponseDTO> holidayResponses = nagerFeignClient.findHolidays(year, code);
            List<RegisterHolidayCommand> commands = holidayResponses.stream()
                    .map(NagerHolidayResponseDTO::toCommand)
                    .toList();

            holidayCommands.addAll(commands);
        }

        return holidayCommands;
    }
}
