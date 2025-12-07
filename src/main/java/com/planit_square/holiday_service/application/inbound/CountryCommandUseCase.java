package com.planit_square.holiday_service.application.inbound;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterCountryCommand;

import java.util.List;

public interface CountryCommandUseCase {

    void initialize(List<RegisterCountryCommand> commands);

    void refresh(List<RegisterCountryCommand> commands);
}
