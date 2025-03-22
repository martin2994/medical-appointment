package com.project.medicalappointment.visit.rest.model;

import com.project.medicalappointment.visit.application.model.Reason;
import com.project.medicalappointment.visit.application.model.Type;

import java.time.LocalDate;
import java.time.LocalTime;

public record VisitDTO(Long id, LocalDate date, LocalTime time, Type type, Reason reason, String familyHistory) {}
