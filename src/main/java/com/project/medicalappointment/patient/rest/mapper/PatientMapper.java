package com.project.medicalappointment.patient.rest.mapper;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.patient.rest.model.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDTO toDTO(Patient patient);
    Patient toPatient(PatientDTO patientDTO);
    Page<PatientDTO> toDTO(Page<Patient> patients);
}
