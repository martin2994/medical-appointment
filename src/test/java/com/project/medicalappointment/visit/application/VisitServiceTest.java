package com.project.medicalappointment.visit.application;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.PatientService;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.visit.application.VisitDatabasePort;
import com.project.medicalappointment.visit.application.VisitService;
import com.project.medicalappointment.visit.application.model.Visit;
import com.project.medicalappointment.visit.application.model.VisitReason;
import com.project.medicalappointment.visit.application.model.VisitType;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class VisitServiceTest {

    @InjectMock
    VisitDatabasePort visitDatabasePort;

    @InjectMock
    PatientService patientService;

    @Inject
    VisitService visitService;

    private Visit visit;
    private Patient patient;
    private UUID visitId;

    @BeforeEach
    void setUp() {
        visitId = UUID.randomUUID();
        var patientId = UUID.randomUUID();
        patient = new Patient(patientId, "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");
        visit = new Visit(visitId, patientId, LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");
    }

    @Test
    public void createVisit() {
        when(patientService.getPatientById(visit.patientUUID())).thenReturn(patient);
        when(visitDatabasePort.createVisit(patient, visit)).thenReturn(visitId);

        UUID createdVisitId = visitService.createVisit(visit);

        assertEquals(visitId, createdVisitId);
        verify(visitDatabasePort, times(1)).createVisit(patient, visit);
    }

    @Test
    public void getVisits() {
        Page<Visit> visitPage = new Page<>(0,10,List.of(visit));
        when(visitDatabasePort.getVisits(0,10)).thenReturn(visitPage);

        Page<Visit> visits = visitService.getVisits(0,10);

        assertNotNull(visits);
        assertEquals(1, visits.content().size());
        assertEquals(visit.date(), visits.content().getFirst().date());
        assertEquals(visit.time(), visits.content().getFirst().time());
        verify(visitDatabasePort, times(1)).getVisits(0,10);
    }

    @Test
    public void getVisitById() {
        when(visitDatabasePort.getVisitById(visitId)).thenReturn(visit);

        Visit fetchedVisit = visitService.getVisitById(visitId);

        assertNotNull(fetchedVisit);
        assertEquals(visitId, fetchedVisit.uuid());
        assertEquals(visit.date(), fetchedVisit.date());
        assertEquals(visit.time(), fetchedVisit.time());
        verify(visitDatabasePort, times(1)).getVisitById(visitId);
    }

    @Test
    public void updateVisit() {
        when(visitDatabasePort.updateVisit(visit)).thenReturn(visit);

        Visit updatedVisit = visitService.updateVisit(visit);

        assertNotNull(updatedVisit);
        assertEquals(visitId, updatedVisit.uuid());
        assertEquals(visit.date(), updatedVisit.date());
        assertEquals(visit.time(), updatedVisit.time());
        verify(visitDatabasePort, times(1)).updateVisit(visit);
    }

    @Test
    public void deleteVisit() {
        doNothing().when(visitDatabasePort).deleteVisit(visitId);

        visitService.deleteVisit(visitId);

        verify(visitDatabasePort, times(1)).deleteVisit(visitId);
    }
}
