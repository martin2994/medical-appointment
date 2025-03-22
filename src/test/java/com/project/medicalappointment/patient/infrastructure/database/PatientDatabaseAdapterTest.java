package com.project.medicalappointment.patient.infrastructure.database;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.PatientDatabasePort;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.patient.infrastructure.database.mapper.PatientMapper;
import com.project.medicalappointment.patient.infrastructure.database.model.PatientEntity;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class PatientDatabaseAdapterTest {

    @InjectMock
    Session session;

    @Inject
    PatientDatabasePort patientDatabaseAdapter;

    private Patient patient;
    private UUID patientId;

    @BeforeEach
    void setUp() {
        PanacheMock.mock(PatientEntity.class);
        patientId = UUID.randomUUID();
        patient = new Patient(patientId, "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");
    }

    @Test
    void createPatient() {
        Mockito.doNothing().when(session).persist(Mockito.any());

        UUID createdPatientId = patientDatabaseAdapter.createPatient(patient);

        assertEquals(patientId, createdPatientId);
        verify(session, times(1)).persist(Mockito.any());
    }

    @Test
    void getPatients() {
        List<PanacheEntityBase> patientEntities = List.of(PatientMapper.INSTANCE.toEntity(patient));
        PanacheQuery<PanacheEntityBase> query = mock(PanacheQuery.class);

        when(PatientEntity.findAll()).thenReturn(query);
        when(query.page(anyInt(), anyInt())).thenReturn(query);
        when(query.list()).thenReturn(patientEntities);

        Page<Patient> patients = patientDatabaseAdapter.getPatients(0,10);

        assertNotNull(patients);
        assertEquals(1, patients.content().size());
        PanacheMock.verify(PatientEntity.class, times(1)).findAll();
    }

    @Test
    void getPatientById() {
        when(PatientEntity.findById(patientId)).thenReturn(PatientMapper.INSTANCE.toEntity(patient));

        Patient fetchedPatient = patientDatabaseAdapter.getPatientById(patientId);

        assertNotNull(fetchedPatient);
        assertEquals(patientId, fetchedPatient.uuid());
        PanacheMock.verify(PatientEntity.class, times(1)).findById(any());
    }

    @Test
    void updatePatient() {
        when(PatientEntity.update(anyString(), any(Parameters.class))).thenReturn(1);

        Patient updatedPatient = patientDatabaseAdapter.updatePatient(patient);

        assertNotNull(updatedPatient);
        PanacheMock.verify(PatientEntity.class, times(1)).update(anyString(), any(Parameters.class));
    }

    @Test
    void deletePatient() {
        when(PatientEntity.deleteById(any())).thenReturn(true);

        patientDatabaseAdapter.deletePatient(patientId);

        PanacheMock.verify(PatientEntity.class, times(1)).deleteById(any());
    }
}
