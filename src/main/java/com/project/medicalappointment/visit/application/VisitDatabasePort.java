package com.project.medicalappointment.visit.application;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.visit.application.model.Visit;

import java.util.UUID;

/**
 * Port which defines all mandatory method for a database adapter to handle visit
 */
public interface VisitDatabasePort {

    UUID createVisit(Patient patient, Visit Visit);

    Page<Visit> getVisits(int page, int pageSize);

    Visit getVisitById(UUID id);

    Visit updateVisit(Visit Visit);

    void deleteVisit(UUID id);
}
