package com.project.medicalappointment.visit.rest;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.visit.application.VisitService;
import com.project.medicalappointment.visit.application.model.Visit;
import com.project.medicalappointment.visit.application.model.VisitReason;
import com.project.medicalappointment.visit.application.model.VisitType;
import com.project.medicalappointment.visit.rest.model.VisitDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
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
class VisitResourceTest {

    @InjectMock
    VisitService visitService;

    @Nested
    class GetVisits {

        @Test
        public void ok() {
            Visit visit = new Visit(UUID.randomUUID(), UUID.randomUUID(), LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");
            Page<Visit> page = new Page<>(0, 10, List.of(visit));
            when(visitService.getVisits(0, 10)).thenReturn(page);

            Page<VisitDTO> responseBody = given()
                    .when().get("/visits")
                    .then()
                    .statusCode(OK.getStatusCode())
                    .extract()
                    .body()
                    .as(new TypeRef<>() {
                    });

            assertEquals(1, responseBody.content().size());
            assertEquals(visit.uuid(), responseBody.content().getFirst().uuid());
            assertEquals(visit.type(), responseBody.content().getFirst().type());
            assertEquals(visit.reason(), responseBody.content().getFirst().reason());
        }
    }

    @Nested
    class AddNewVisit {

        @Test
        public void ok() {
            VisitDTO visitDTO = new VisitDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");
            Visit visit = new Visit(UUID.randomUUID(), UUID.randomUUID(), LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");
            when(visitService.createVisit(any(Visit.class))).thenReturn(visit.uuid());
            Response response = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(visitDTO)
                    .when()
                    .post("/visits")
                    .then()
                    .statusCode(CREATED.getStatusCode())
                    .extract()
                    .response();

            assertTrue(response.getHeader("Location").contains("/visits/"));
        }
    }

    @Nested
    class GetVisitById {

        @Test
        public void ok() {
            UUID visitId = UUID.randomUUID();
            UUID patientId = UUID.randomUUID();
            Visit visit = new Visit(visitId, patientId, LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");

            when(visitService.getVisitById(visitId)).thenReturn(visit);

            given()
                    .when().get("/visits/" + visitId)
                    .then()
                    .statusCode(OK.getStatusCode())
                    .body("uuid", equalTo(visitId.toString()))
                    .body("patientUUID", equalTo(patientId.toString()))
                    .body("reason", equalTo(visit.reason().toString()));
        }

    }

    @Nested
    class UpdateVisit {

        @Test
        public void ok() {
            UUID visitId = UUID.randomUUID();
            UUID patientId = UUID.randomUUID();
            VisitDTO visitDTO = new VisitDTO(visitId, patientId, LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");
            Visit visit = new Visit(visitId, patientId, LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");

            when(visitService.updateVisit(any(Visit.class))).thenReturn(visit);

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(visitDTO)
                    .when().put("/visits/" + visitId)
                    .then()
                    .statusCode(OK.getStatusCode())
                    .body("uuid", equalTo(visitId.toString()))
                    .body("patientUUID", equalTo(patientId.toString()))
                    .body("reason", equalTo(visit.reason().toString()));
        }

        @Test
        public void badRequest() {
            UUID visitId = UUID.randomUUID();
            VisitDTO visitDTO = new VisitDTO(UUID.randomUUID(), UUID.randomUUID(), LocalDate.of(2050,1,1), LocalTime.of(10,10), VisitType.OFFICE_VISIT, VisitReason.FIRST_VISIT, "First visit");

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(visitDTO)
                    .when().put("/visits/" + visitId)
                    .then()
                    .statusCode(BAD_REQUEST.getStatusCode());
        }
    }

    @Nested
    class DeleteVisit {

        @Test
        public void ok() {
            UUID visitId = UUID.randomUUID();

            doNothing().when(visitService).deleteVisit(visitId);

            given()
                    .when().delete("/visits/" + visitId)
                    .then()
                    .statusCode(NO_CONTENT.getStatusCode());
        }
    }

}