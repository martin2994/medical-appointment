package com.project.medicalappointment.patient.infrastructure.database;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.PatientDatabasePort;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.patient.infrastructure.database.mapper.PatientMapper;
import com.project.medicalappointment.patient.infrastructure.database.model.PatientEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PatientDatabaseAdapter implements PatientDatabasePort {

    @Override
    public UUID createPatient(Patient patient) {
        PatientEntity patientEntity = PatientMapper.INSTANCE.toEntity(patient);
        patientEntity.persist();
        return patientEntity.getUuid();
    }

    @Override
    public Page<Patient> getPatients() {
        List<PatientEntity> patientEntities = PatientEntity.listAll();
        Page<PatientEntity> patientEntityPage = new Page<>(patientEntities);
        return PatientMapper.INSTANCE.toPatient(patientEntityPage);
    }

    @Override
    public Patient getPatientById(UUID id) {
        return PatientMapper.INSTANCE.toPatient(PatientEntity.findById(id));
    }

    @Override
    public Patient updatePatient(Patient patient) {
        PatientEntity.update("name =:name, surname =:surname where id=:id",
                Parameters.with("name", patient.name())
                        .and("surname", patient.surname())
                        .and("id", patient.uuid()));
        return patient;
    }

    @Override
    public void deletePatient(UUID id) {
        PatientEntity.deleteById(id);
    }
}
