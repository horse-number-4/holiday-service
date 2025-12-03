package com.planit_square.holiday_service.adapter.client;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;

public record NagerCountryResponseDTO(
        String countryCode,
        String name
) {
    public RegisterCountryCommand toCommand() {
        return new RegisterCountryCommand(countryCode, name);
    }
}
