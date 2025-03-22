package com.project.medicalappointment.patient.application;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;

import java.util.UUID;

/**
 * Port which defines all mandatory method for a database adapter to handle patient
 */
public interface PatientDatabasePort {

    UUID createPatient(Patient patient);

    Page<Patient> getPatients(int page, int pageSize);

    Patient getPatientById(UUID id);

    Patient updatePatient(Patient patient);

    void deletePatient(UUID id);

}
