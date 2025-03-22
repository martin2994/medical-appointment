package com.project.medicalappointment.patient.application;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

/**
 * Functional Service which defines all methods to handle patients (CRUD)
 * Must not be associated to an infrastructure class
 */
@ApplicationScoped
public class PatientService {

    private final PatientDatabasePort patientDatabasePort;

    public PatientService(PatientDatabasePort patientDatabasePort) {
        this.patientDatabasePort = patientDatabasePort;
    }

    /**
     * Create a new patient
     * @param patient the new patient to create
     * @return the UUID of the new patient
     */
    @Transactional
    public UUID createPatient(Patient patient) {
        return this.patientDatabasePort.createPatient(patient);
    }

    /**
     * Get a paginated list of patients
     * @param page the current page to get
     * @param pageSize the limit of patient in a page
     * @return the paginated list of patient
     */
    public Page<Patient> getPatients(int page, int pageSize) {
        return this.patientDatabasePort.getPatients(page, pageSize);
    }

    /**
     * Get a patient by its UUID
     * @param id the UUID of a patient
     * @return the patient information
     */
    public Patient getPatientById(UUID id) {
        return this.patientDatabasePort.getPatientById(id);
    }

    /**
     * Update a patient
     * @param patient the updated information of a patient
     * @return the updated information of the patient
     */
    @Transactional
    public Patient updatePatient(Patient patient) {
        return this.patientDatabasePort.updatePatient(patient);
    }

    /**
     * Delete a patient by its UUID
     * @param id the UUID of the patient to delete
     */
    @Transactional
    public void deletePatient(UUID id) {
        this.patientDatabasePort.deletePatient(id);
    }

}
