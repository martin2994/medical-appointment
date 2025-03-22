package com.project.medicalappointment.patient.application;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class PatientServiceTest {

    @InjectMock
    PatientDatabasePort patientDatabasePort;

    @Inject
    PatientService patientService;

    private Patient patient;
    private UUID patientId;

    @BeforeEach
    void setUp() {
        patientId = UUID.randomUUID();
        patient = new Patient(patientId, "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");
    }

    @Test
    public void createPatient() {
        when(patientDatabasePort.createPatient(patient)).thenReturn(patientId);

        UUID createdPatientId = patientService.createPatient(patient);

        assertEquals(patientId, createdPatientId);
        verify(patientDatabasePort, times(1)).createPatient(patient);
    }

    @Test
    public void getPatients() {
        Page<Patient> patientPage = new Page<>(0,10,List.of(patient));
        when(patientDatabasePort.getPatients(0,10)).thenReturn(patientPage);

        Page<Patient> patients = patientService.getPatients(0,10);

        assertNotNull(patients);
        assertEquals(1, patients.content().size());
        assertEquals(patient.name(), patients.content().getFirst().name());
        verify(patientDatabasePort, times(1)).getPatients(0,10);
    }

    @Test
    public void getPatientById() {
        when(patientDatabasePort.getPatientById(patientId)).thenReturn(patient);

        Patient fetchedPatient = patientService.getPatientById(patientId);

        assertNotNull(fetchedPatient);
        assertEquals(patientId, fetchedPatient.uuid());
        assertEquals(patient.name(), fetchedPatient.name());
        verify(patientDatabasePort, times(1)).getPatientById(patientId);
    }

    @Test
    public void updatePatient() {
        when(patientDatabasePort.updatePatient(patient)).thenReturn(patient);

        Patient updatedPatient = patientService.updatePatient(patient);

        assertNotNull(updatedPatient);
        assertEquals(patientId, updatedPatient.uuid());
        assertEquals(patient.name(), updatedPatient.name());
        verify(patientDatabasePort, times(1)).updatePatient(patient);
    }

    @Test
    public void deletePatient() {
        doNothing().when(patientDatabasePort).deletePatient(patientId);

        patientService.deletePatient(patientId);

        verify(patientDatabasePort, times(1)).deletePatient(patientId);
    }
}
