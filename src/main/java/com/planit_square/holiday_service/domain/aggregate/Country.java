package com.planit_square.holiday_service.domain.aggregate;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import lombok.Getter;

@Getter
public class Country {

    private String code;
    private String name;

    public static Country register(RegisterCountryCommand command) {

        Country country = new Country();

        country.code = command.code();
        country.name = command.name();

        return country;
    }
}
