package com.planit_square.holiday_service.domain.aggregate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHoliday is a Querydsl query type for Holiday
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHoliday extends EntityPathBase<Holiday> {

    private static final long serialVersionUID = 1193610514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHoliday holiday = new QHoliday("holiday");

    public final QCountry country;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.planit_square.holiday_service.domain.valueobject.QHolidayName name;

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QHoliday(String variable) {
        this(Holiday.class, forVariable(variable), INITS);
    }

    public QHoliday(Path<? extends Holiday> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHoliday(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHoliday(PathMetadata metadata, PathInits inits) {
        this(Holiday.class, metadata, inits);
    }

    public QHoliday(Class<? extends Holiday> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountry(forProperty("country")) : null;
        this.name = inits.isInitialized("name") ? new com.planit_square.holiday_service.domain.valueobject.QHolidayName(forProperty("name")) : null;
    }

}

