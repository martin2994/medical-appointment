package com.project.medicalappointment.patient.application.model;

import java.time.LocalDate;
import java.util.UUID;

public record Patient(UUID uuid, String name, String surname, LocalDate birthdate, String socialSecurityNumber) {
}
