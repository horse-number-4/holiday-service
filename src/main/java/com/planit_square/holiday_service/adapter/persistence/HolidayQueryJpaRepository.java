package com.planit_square.holiday_service.adapter.persistence;

import com.planit_square.holiday_service.adapter.web.HolidayResponse;
import com.planit_square.holiday_service.adapter.web.HolidaySearchCondition;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.QCountry;
import com.planit_square.holiday_service.domain.aggregate.QHoliday;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.planit_square.holiday_service.domain.aggregate.QCountry.*;
import static com.planit_square.holiday_service.domain.aggregate.QHoliday.*;

@RequiredArgsConstructor
@Component
public class HolidayQueryJpaRepository {

    private final JPAQueryFactory queryFactory;

    public Page<HolidayResponse> findHolidays(HolidaySearchCondition condition, Pageable pageable) {

        Long totalCount = queryFactory
                .select(holiday.count())
                .from(holiday)
                .fetchOne();

        List<HolidayResponse> responses = queryFactory
                .select(Projections.constructor(HolidayResponse.class,
                        holiday.id,
                        holiday.name.name,
                        holiday.name.localName,
                        holiday.date,
                        holiday.year,
                        Projections.constructor(HolidayResponse.CountryResponse.class,
                                country.code,
                                country.name)
                ))
                .from(holiday)
                .join(holiday.country, country)
                .where(
                        equalsHolidayYear(condition.year()),
                        equalsCode(condition.code())
                )
                .orderBy(holiday.date.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(responses, pageable, totalCount);
    }

    private BooleanExpression equalsHolidayYear(Integer year) {
        return Objects.nonNull(year) ? holiday.year.eq(year) : null;
    }

    private BooleanExpression equalsCode(String code) {
        return Objects.nonNull(code) ? holiday.country.code.eq(code) : null;
    }
}
