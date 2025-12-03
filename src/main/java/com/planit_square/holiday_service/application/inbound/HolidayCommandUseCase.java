package com.planit_square.holiday_service.application.inbound;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;

import java.util.List;

public interface HolidayCommandUseCase {

    void register(String code,List<RegisterHolidayCommand> commands);
}
