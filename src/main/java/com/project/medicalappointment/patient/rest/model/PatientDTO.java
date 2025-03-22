package com.project.medicalappointment.patient.rest.model;

import com.project.medicalappointment.common.validator.ConstraintGroups;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/**
 * The patient DTO model
 * @param uuid the unique id of a patient (must be not null during the update)
 * @param name the name of the patient (must be not null)
 * @param surname the surname of the patient (must be not null)
 * @param birthdate the birthdate of the patient (must be not null)
 * @param socialSecurityNumber the social security number of the patient (must be not null)
 */
public record PatientDTO(@NotNull(groups = ConstraintGroups.Update.class)UUID uuid,
                         @NotNull String name,
                         @NotNull String surname,
                         @NotNull LocalDate birthdate,
                         @NotNull String socialSecurityNumber) {
}
