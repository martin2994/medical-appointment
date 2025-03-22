package com.project.medicalappointment.visit.application.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * The visit functional model
 * @param uuid the UUID of the visit
 * @param patientUUID the patient of the visit
 * @param date the date of the visit
 * @param time the time of the visit
 * @param type the type of visit
 * @param reason the reason of the visit
 * @param familyHistory the family history of the patient
 */
public record Visit(UUID uuid, UUID patientUUID, LocalDate date, LocalTime time, VisitType type, VisitReason reason, String familyHistory) {
}
