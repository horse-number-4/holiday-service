package com.planit_square.holiday_service.domain.aggregate;

import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import com.planit_square.holiday_service.domain.valueobject.HolidayName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "holidays")
@Entity
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code")
    private Country country;

    @Column(name = "holiday_year")
    private Integer year;

    @Column(name = "holiday_date")
    private LocalDate date;

    @Embedded
    private HolidayName name;

    public static Holiday register(Country country, RegisterHolidayCommand command) {

        Holiday holiday = new Holiday();

        holiday.country = country;
        holiday.year = command.year();
        holiday.date = command.date();
        holiday.name = new HolidayName(command.name(), command.localName());

        return holiday;
    }
}
