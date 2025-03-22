package com.project.medicalappointment.visit.infrastructure;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.visit.application.VisitDatabasePort;
import com.project.medicalappointment.visit.application.model.Visit;
import com.project.medicalappointment.visit.application.model.VisitReason;
import com.project.medicalappointment.visit.application.model.VisitType;
import com.project.medicalappointment.visit.infrastructure.database.mapper.VisitMapper;
import com.project.medicalappointment.visit.infrastructure.database.model.VisitEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class VisitDatabaseAdapterTest {

    @InjectMock
    Session session;

    @Inject
    VisitDatabasePort visitDatabaseAdapter;

    private Visit visit;
    private Patient patient;
    private UUID visitId;

    @BeforeEach
    void setUp() {
        PanacheMock.mock(VisitEntity.class);
        visitId = UUID.randomUUID();
        var patientId = UUID.randomUUID();
        patient = new Patient(patientId, "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");
        visit = new Visit(visitId, patientId, LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");
    }

    @Test
    void createVisit() {
        Mockito.doNothing().when(session).persist(Mockito.any());

        UUID createdVisitId = visitDatabaseAdapter.createVisit(patient, visit);

        assertEquals(visitId, createdVisitId);
        verify(session, times(1)).persist(Mockito.any());
    }

    @Test
    void getVisits() {
        List<PanacheEntityBase> visitEntities = List.of(VisitMapper.INSTANCE.toEntity(visit));
        PanacheQuery<PanacheEntityBase> query = mock(PanacheQuery.class);

        when(VisitEntity.findAll()).thenReturn(query);
        when(query.page(anyInt(), anyInt())).thenReturn(query);
        when(query.list()).thenReturn(visitEntities);

        Page<Visit> visits = visitDatabaseAdapter.getVisits(0,10);

        assertNotNull(visits);
        assertEquals(1, visits.content().size());
        PanacheMock.verify(VisitEntity.class, times(1)).findAll();
    }

    @Test
    void getVisitById() {
        when(VisitEntity.findById(visitId)).thenReturn(VisitMapper.INSTANCE.toEntity(visit));

        Visit fetchedVisit = visitDatabaseAdapter.getVisitById(visitId);

        assertNotNull(fetchedVisit);
        assertEquals(visitId, fetchedVisit.uuid());
        PanacheMock.verify(VisitEntity.class, times(1)).findById(any());
    }

    @Test
    void updateVisit() {
        when(VisitEntity.update(anyString(), any(Parameters.class))).thenReturn(1);

        Visit updatedVisit = visitDatabaseAdapter.updateVisit(visit);

        assertNotNull(updatedVisit);
        PanacheMock.verify(VisitEntity.class, times(1)).update(anyString(), any(Parameters.class));
    }

    @Test
    void deleteVisit() {
        when(VisitEntity.deleteById(any())).thenReturn(true);

        visitDatabaseAdapter.deleteVisit(visitId);

        PanacheMock.verify(VisitEntity.class, times(1)).deleteById(any());
    }
}
