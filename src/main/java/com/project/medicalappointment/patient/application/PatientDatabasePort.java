package com.project.medicalappointment.patient.application;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;

import java.util.UUID;

public interface PatientDatabasePort {

    UUID createPatient(Patient patient);

    Page<Patient> getPatients();

    Patient getPatientById(UUID id);

    Patient updatePatient(Patient patient);

    void deletePatient(UUID id);

}
