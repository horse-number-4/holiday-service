package com.planit_square.holiday_service.domain.valueobject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHolidayName is a Querydsl query type for HolidayName
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QHolidayName extends BeanPath<HolidayName> {

    private static final long serialVersionUID = 1483850030L;

    public static final QHolidayName holidayName = new QHolidayName("holidayName");

    public final StringPath localName = createString("localName");

    public final StringPath name = createString("name");

    public QHolidayName(String variable) {
        super(HolidayName.class, forVariable(variable));
    }

    public QHolidayName(Path<? extends HolidayName> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHolidayName(PathMetadata metadata) {
        super(HolidayName.class, metadata);
    }

}

