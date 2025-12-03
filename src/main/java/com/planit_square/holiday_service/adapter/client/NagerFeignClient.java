package com.planit_square.holiday_service.adapter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "NagerFeignClient", url = "https://date.nager.at/api/v3")
public interface NagerFeignClient {

    @GetMapping("/AvailableCountries")
    List<NagerCountryResponseDTO> findCountries();

    @GetMapping("/PublicHolidays/{year}/{countryCode}")
    List<NagerHolidayResponseDTO> findHolidays(@PathVariable("year") int year, @PathVariable("countryCode") String countryCode);
}
