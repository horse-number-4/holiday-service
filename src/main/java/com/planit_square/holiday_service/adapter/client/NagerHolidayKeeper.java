package com.planit_square.holiday_service.adapter.client;

import com.planit_square.holiday_service.application.outbound.HolidayKeeper;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

        int startedYear = getStartYear();
        int endedYear = getEndedYear();

        List<RegisterHolidayCommand> holidayCommands = new ArrayList<>();

        for (int year = startedYear; year <= endedYear; year++) {

            List<NagerHolidayResponseDTO> holidayResponses = nagerFeignClient.findHolidays(year, code);

            for (NagerHolidayResponseDTO response : holidayResponses) {
                holidayCommands.add(response.toCommand());
            }
        }

        return holidayCommands;
    }

    @Override
    public List<RegisterHolidayCommand> findHolidays(int year, String code) {
        List<NagerHolidayResponseDTO> holidayResponses = nagerFeignClient.findHolidays(year, code);
        return holidayResponses.stream()
                .map(NagerHolidayResponseDTO::toCommand)
                .toList();
    }

    private int getStartYear() {
        return LocalDate.now().minusYears(5).getYear();
    }

    private int getEndedYear() {
        return LocalDate.now().getYear();
    }
}
