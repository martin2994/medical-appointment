package com.project.medicalappointment.visit.application;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.PatientService;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.visit.application.model.Visit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.util.UUID;

/**
 * Functional Service which defines all methods to handle visit (CRUD)
 * Must not be associated to an infrastructure class
 */
@ApplicationScoped
public class VisitService {

    private final VisitDatabasePort visitDatabasePort;

    private final PatientService patientService;

    public VisitService(VisitDatabasePort visitDatabasePort, PatientService patientService) {
        this.visitDatabasePort = visitDatabasePort;
        this.patientService = patientService;
    }

    /**
     * Create a new visit
     * @param visit the new visit to create
     * @return the UUID of the new visit
     */
    @Transactional
    public UUID createVisit(Visit visit) {
        Patient patient = patientService.getPatientById(visit.patientUUID());
        if (patient == null) {
            throw new WebApplicationException("Patient not found", 404);
        }
        return this.visitDatabasePort.createVisit(patient, visit);
    }

    /**
     * Get a visit by its UUID
     * @param id the UUID of the visit
     * @return the visit information
     */
    public Visit getVisitById(UUID id) {
        return this.visitDatabasePort.getVisitById(id);
    }

    /**
     * Get a paginated list of visit
     * @param page the page to get
     * @param pageSize the limit of visit in a page
     * @return the paginated list of visit
     */
    public Page<Visit> getVisits(int page, int pageSize) {
        return this.visitDatabasePort.getVisits(page, pageSize);
    }

    /**
     * Update a visit by its UUID
     * @param visit the updated information of a visit
     * @return the updated information
     */
    @Transactional
    public Visit updateVisit(Visit visit) {
        return this.visitDatabasePort.updateVisit(visit);
    }

    /**
     * Delete a visit by its UUID
     * @param id the UUID of the visit to delete
     */
    @Transactional
    public void deleteVisit(UUID id) {
        this.visitDatabasePort.deleteVisit(id);
    }

}
