package com.planit_square.holiday_service.domain.aggregate;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "countries")
@Entity
public class Country {

    @Id
    private String code;
    private String name;

    public static Country register(RegisterCountryCommand command) {

        Country country = new Country();

        country.code = command.code();
        country.name = command.name();

        return country;
    }
}
