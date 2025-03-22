package com.project.medicalappointment.patient.infrastructure.database.mapper;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.patient.infrastructure.database.model.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper to transform functional patient to patient entity and inversely
 */
@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientEntity toEntity(Patient patient);
    Patient toPatient(PatientEntity patientEntity);
    Page<Patient> toPatient(Page<PatientEntity> patientEntities);
}
