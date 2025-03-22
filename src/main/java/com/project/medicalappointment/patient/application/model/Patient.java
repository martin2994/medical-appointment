package com.project.medicalappointment.patient.application.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * The patient functional model
 * @param uuid the unique id of a patient
 * @param name the name of the patient
 * @param surname the surname of the patient
 * @param birthdate the birthdate of the patient
 * @param socialSecurityNumber the social security number of the patient
 */
public record Patient(UUID uuid, String name, String surname, LocalDate birthdate, String socialSecurityNumber) {
}
