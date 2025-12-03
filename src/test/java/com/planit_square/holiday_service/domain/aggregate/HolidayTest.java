package com.planit_square.holiday_service.domain.aggregate;

import com.planit_square.holiday_service.DomainFixture;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HolidayTest {

    Country country;

    @BeforeEach
    void setUp() {
        country = Country.register(DomainFixture.createRegisterCountryCommand());
    }

    @Test
    void register() {

        RegisterHolidayCommand holidayCommand = DomainFixture.createRegisterHolidayCommand();

        Holiday holiday = Holiday.register(country, holidayCommand);

        assertThat(holiday).isNotNull();
        assertThat(holiday.getYear()).isEqualTo(2025);
        assertThat(holiday.getCountry().getCode()).isEqualTo("KR");
    }
}