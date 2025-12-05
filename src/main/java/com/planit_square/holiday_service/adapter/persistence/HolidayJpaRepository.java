package com.planit_square.holiday_service.adapter.persistence;

import com.planit_square.holiday_service.domain.aggregate.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HolidayJpaRepository extends JpaRepository<Holiday, Long> {

    @Query("SELECT h FROM Holiday h WHERE h.year = :year AND h.country.code = :code")
    List<Holiday> findByYearAndCode(int year, String code);
}
