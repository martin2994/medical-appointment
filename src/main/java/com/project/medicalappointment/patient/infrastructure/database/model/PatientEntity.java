package com.project.medicalappointment.patient.infrastructure.database.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

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

    @Column(name ="social_security_number", unique = true, nullable = false, updatable = false, length = 15)
    private String socialSecurityNumber;

    @PrePersist
    public void generateUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
