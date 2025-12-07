package com.planit_square.holiday_service.domain.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class HolidayName {

    private String name;
    private String localName;

    protected HolidayName() {}

    public HolidayName(String name, String localName) {
        this.name = name;
        this.localName = localName;
    }
}
