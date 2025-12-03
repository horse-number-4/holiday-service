package com.planit_square.holiday_service.domain.aggregate;

import com.planit_square.holiday_service.DomainFixture;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryTest {

    @Test
    void register() {

        RegisterCountryCommand command = DomainFixture.createRegisterCountryCommand();

        Country country = Country.register(command);

        assertThat(country.getCode()).isEqualTo("KR");
        assertThat(country.getName()).isEqualTo("South Korea");
    }
}