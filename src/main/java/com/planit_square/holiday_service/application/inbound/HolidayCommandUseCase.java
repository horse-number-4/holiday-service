package com.planit_square.holiday_service.application.inbound;

import com.planit_square.holiday_service.domain.aggregate.command.RefreshHolidayCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;

import java.util.List;

public interface HolidayCommandUseCase {

    void initialize(List<RegisterHolidayCommand> commands);

    void refresh(RefreshHolidayCommand command);

    void delete(int year, String code);
}
