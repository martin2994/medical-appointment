package com.project.medicalappointment.visit.infrastructure.database;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.patient.infrastructure.database.mapper.PatientMapper;
import com.project.medicalappointment.patient.infrastructure.database.model.PatientEntity;
import com.project.medicalappointment.visit.application.VisitDatabasePort;
import com.project.medicalappointment.visit.application.model.Visit;
import com.project.medicalappointment.visit.infrastructure.database.mapper.VisitMapper;
import com.project.medicalappointment.visit.infrastructure.database.model.VisitEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the Visit database port to handle all CRUD method in a relational database
 */
@ApplicationScoped
public class VisitDatabaseAdapter implements VisitDatabasePort {

    @Override
    public UUID createVisit(Patient patient, Visit visit) {
        PatientEntity patientEntity = PatientMapper.INSTANCE.toEntity(patient);
        VisitEntity visitEntity = VisitMapper.INSTANCE.toEntity(visit);
        visitEntity.setPatient(patientEntity);
        visitEntity.persist();
        return visitEntity.getUuid();
    }

    @Override
    public Page<Visit> getVisits(int page, int pageSize) {
        PanacheQuery<VisitEntity> query = VisitEntity.findAll();
        List<VisitEntity> visitEntities = query.page(page, pageSize).list();
        Page<VisitEntity> visitEntityPage = new Page<>(page, pageSize, visitEntities);
        return VisitMapper.INSTANCE.toVisit(visitEntityPage);
    }

    @Override
    public Visit getVisitById(UUID id) {
        return VisitMapper.INSTANCE.toVisit(VisitEntity.findById(id));
    }

    @Override
    public Visit updateVisit(Visit visit) {
        VisitEntity.update("""
                        date =:date, time =:time, type =:type, reason =:reason, familyHistory =:familyHistory
                        where id=:id
                        """,
                Parameters.with("date", visit.date())
                        .and("time", visit.time())
                        .and("type", visit.type())
                        .and("reason", visit.reason())
                        .and("familyHistory", visit.familyHistory())
                        .and("id", visit.uuid()));
        return visit;
    }

    @Override
    public void deleteVisit(UUID id) {
        VisitEntity.deleteById(id);
    }
}
