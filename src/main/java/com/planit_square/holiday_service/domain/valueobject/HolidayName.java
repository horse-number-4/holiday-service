package com.planit_square.holiday_service.domain.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public record HolidayName(
        String name,
        String localName
) {}
