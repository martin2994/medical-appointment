package com.project.medicalappointment.patient.rest;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.patient.application.PatientService;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.patient.rest.mapper.PatientMapper;
import com.project.medicalappointment.patient.rest.model.PatientDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.UUID;

@Path("/patients")
public class PatientResource {

    private final PatientService patientService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);

    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<PatientDTO> getPatients() {
        LOGGER.info("getPatients called");
        return PatientMapper.INSTANCE.toDTO(this.patientService.getPatients());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPatient(PatientDTO patientDTO) {
        LOGGER.info("Adding patient: {}", patientDTO);
        UUID id = this.patientService.createPatient(PatientMapper.INSTANCE.toPatient(patientDTO));
        return Response.created(URI.create("/patients/" + id.toString())).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public PatientDTO getPatientById(@PathParam("uuid") UUID uuid) {
        LOGGER.info("getPatientById called with {}", uuid);
        return PatientMapper.INSTANCE.toDTO(this.patientService.getPatientById(uuid));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public PatientDTO updatePatient(@PathParam("uuid") UUID uuid, PatientDTO patientDTO) {
        LOGGER.info("Updating patient: {}", patientDTO);
        if(!uuid.equals(patientDTO.uuid())){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Patient patient = PatientMapper.INSTANCE.toPatient(patientDTO);
        return PatientMapper.INSTANCE.toDTO(this.patientService.updatePatient(patient));
    }

    @DELETE
    @Path("/{uuid}")
    public Response deletePatient(@PathParam("uuid") UUID uuid) {
        LOGGER.info("Deleting patient: {}", uuid);
        this.patientService.deletePatient(uuid);
        return Response.ok().build();
    }

}
