package com.project.medicalappointment.patient.rest.model;

import java.time.LocalDate;
import java.util.UUID;

public record PatientDTO(UUID uuid, String name, String surname, LocalDate birthdate, String socialSecurityNumber) {
}
