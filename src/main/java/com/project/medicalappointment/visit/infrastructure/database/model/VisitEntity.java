package com.project.medicalappointment.visit.infrastructure.database.model;


import com.project.medicalappointment.patient.infrastructure.database.model.PatientEntity;
import com.project.medicalappointment.visit.application.model.VisitReason;
import com.project.medicalappointment.visit.application.model.VisitType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * The visit entity model in a relational database
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "visit")
public class VisitEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_uuid", nullable = false)
    private PatientEntity patient;

    private LocalDate date;

    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private VisitType type;

    @Enumerated(EnumType.STRING)
    private VisitReason reason;

    @Column(name ="family_history")
    private String familyHistory;

    @PrePersist
    public void generateUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
