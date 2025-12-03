package com.planit_square.holiday_service.domain.aggregate.command;

public record RegisterCountryCommand(
        String code,
        String name
) {
}
