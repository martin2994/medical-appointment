package com.project.medicalappointment.visit.rest.mapper;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.visit.application.model.Visit;
import com.project.medicalappointment.visit.rest.model.VisitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper to transform functional visit to visit DTO and inversely
 */
@Mapper
public interface VisitMapper {

    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    Visit toVisit(VisitDTO dto);
    VisitDTO toDTO(Visit visit);
    Page<VisitDTO> toDTO(Page<Visit> visits);
}