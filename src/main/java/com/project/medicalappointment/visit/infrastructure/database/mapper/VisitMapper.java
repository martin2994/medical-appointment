package com.project.medicalappointment.visit.infrastructure.database.mapper;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.visit.application.model.Visit;
import com.project.medicalappointment.visit.infrastructure.database.model.VisitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper to transform functional visit to visit entity and inversely
 */
@Mapper
public interface VisitMapper {

    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    @Mapping(source = "patient.uuid", target = "patientUUID")
    Visit toVisit(VisitEntity entity);
    VisitEntity toEntity(Visit dto);
    Page<Visit> toVisit(Page<VisitEntity> visitEntities);
}