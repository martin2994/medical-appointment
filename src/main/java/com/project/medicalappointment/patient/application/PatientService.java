package com.project.medicalappointment.patient.application;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class PatientService {

    private final PatientDatabasePort patientDatabasePort;

    public PatientService(PatientDatabasePort patientDatabasePort) {
        this.patientDatabasePort = patientDatabasePort;
    }

    @Transactional
    public UUID createPatient(Patient patient) {
        return this.patientDatabasePort.createPatient(patient);
    }

    public Page<Patient> getPatients() {
        return this.patientDatabasePort.getPatients();
    }

    public Patient getPatientById(UUID id) {
        return this.patientDatabasePort.getPatientById(id);
    }

    @Transactional
    public Patient updatePatient(Patient patient) {
        return this.patientDatabasePort.updatePatient(patient);
    }

    @Transactional
    public void deletePatient(UUID id) {
        this.patientDatabasePort.deletePatient(id);
    }

}
