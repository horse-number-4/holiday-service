package com.planit_square.holiday_service.adapter.persistence;

import com.planit_square.holiday_service.domain.aggregate.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CountryJpaRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCode(String code);

    List<Country> findAllByCodeIn(Set<String> codes);
}
