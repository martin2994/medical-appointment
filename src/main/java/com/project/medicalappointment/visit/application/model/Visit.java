package com.project.medicalappointment.visit.application.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Visit(Long id, LocalDate date, LocalTime time, Type type, Reason reason, String familyHistory) {
}
