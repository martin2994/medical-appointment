package com.project.medicalappointment.visit.rest.model;

import com.project.medicalappointment.common.validator.ConstraintGroups;
import com.project.medicalappointment.common.validator.FutureDate;
import com.project.medicalappointment.visit.application.model.VisitReason;
import com.project.medicalappointment.visit.application.model.VisitType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record VisitDTO(@NotNull(groups = ConstraintGroups.Update.class) UUID uuid,
                       @NotNull UUID patientUUID,
                       @FutureDate LocalDate date,
                       LocalTime time,
                       VisitType type,
                       VisitReason reason,
                       String familyHistory) {
}
