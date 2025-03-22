package com.project.medicalappointment.visit.infrastructure.database.model;


import com.project.medicalappointment.visit.application.model.Reason;
import com.project.medicalappointment.visit.application.model.Type;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VisitEntity extends PanacheEntity {

    private LocalDate date;
    private LocalTime time;
    private Type type;
    private Reason reason;
    private String familyHistory;

}
