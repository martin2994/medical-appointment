package com.project.medicalappointment.patient.rest;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.PatientService;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.patient.rest.model.PatientDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@QuarkusTest
class PatientResourceTest {

    @InjectMock
    PatientService patientService;

    @Nested
    class GetPatients {

        @Test
        public void ok() {
            Patient patient = new Patient(UUID.randomUUID(), "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");
            Page<Patient> page = new Page<>(List.of(patient));
            when(patientService.getPatients()).thenReturn(page);

            Page<PatientDTO> responseBody = given()
                    .when().get("/patients")
                    .then()
                    .statusCode(OK.getStatusCode())
                    .extract()
                    .body()
                    .as(new TypeRef<>() {});

            assertEquals(1, responseBody.content().size());
            assertEquals("Dupond", responseBody.content().getFirst().name());
            assertEquals("Jeanne", responseBody.content().getFirst().surname());
        }
    }

    @Nested
    class AddNewPatient {

        @Test
        public void ok() {
            Patient patient = new Patient(UUID.randomUUID(), "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");
            PatientDTO patientDTO = new PatientDTO(UUID.randomUUID(), "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");
            when(patientService.createPatient(any(Patient.class))).thenReturn(patient.uuid());

            Response response = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(patientDTO)
                    .when()
                    .post("/patients")
                    .then()
                    .statusCode(CREATED.getStatusCode())
                    .extract()
                    .response();

            assertTrue(response.getHeader("Location").contains("/patients/"));
        }
    }

    @Nested
    class GetPatientById {

        @Test
        public void ok() {
            UUID patientId = UUID.randomUUID();
            Patient patient = new Patient(patientId, "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");

            when(patientService.getPatientById(patientId)).thenReturn(patient);

            given()
                    .when().get("/patients/" + patientId)
                    .then()
                    .statusCode(OK.getStatusCode())
                    .body("uuid", equalTo(patientId.toString()))
                    .body("name", equalTo("Dupond"));
        }

    }

    @Nested
    class UpdatePatient {

        @Test
        public void ok() {
            UUID patientId = UUID.randomUUID();
            PatientDTO patientDTO = new PatientDTO(patientId, "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");
            Patient patient = new Patient(patientId, "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");

            when(patientService.updatePatient(any(Patient.class))).thenReturn(patient);

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(patientDTO)
                    .when().put("/patients/" + patientId)
                    .then()
                    .statusCode(OK.getStatusCode())
                    .body("uuid", equalTo(patientId.toString()))
                    .body("name", equalTo("Dupond"));
        }

        @Test
        public void badRequest() {
            UUID patientId = UUID.randomUUID();
            PatientDTO patientDTO = new PatientDTO(UUID.randomUUID(), "Dupond", "Jeanne", LocalDate.of(2000, 1, 1), "200133456789012");

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(patientDTO)
                    .when().put("/patients/" + patientId)
                    .then()
                    .statusCode(BAD_REQUEST.getStatusCode());
        }
    }

    @Nested
    class DeletePatient {

        @Test
        public void ok() {
            UUID patientId = UUID.randomUUID();

            doNothing().when(patientService).deletePatient(patientId);

            given()
                    .when().delete("/patients/" + patientId)
                    .then()
                    .statusCode(OK.getStatusCode());
        }
    }

}