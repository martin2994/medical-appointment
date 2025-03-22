package com.project.medicalappointment.patient.infrastructure.database.model;

import com.project.medicalappointment.visit.infrastructure.database.model.VisitEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * The patient entity model in a relational database
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "patient")
public class PatientEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String name;

    private String surname;

    private LocalDate birthdate;

    @Column(name ="social_security_number")
    private String socialSecurityNumber;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VisitEntity> visits;

    @PrePersist
    public void generateUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
