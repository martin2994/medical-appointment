package com.project.medicalappointment.patient.rest;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.common.validator.ConstraintGroups;
import com.project.medicalappointment.patient.application.PatientService;
import com.project.medicalappointment.patient.application.model.Patient;
import com.project.medicalappointment.patient.rest.mapper.PatientMapper;
import com.project.medicalappointment.patient.rest.model.PatientDTO;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.UUID;

/**
 * REST Controller class which defines all endpoints to handle CRUD operation on patient
 */
@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    private final PatientService patientService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);

    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Get a paginated list of patient
     * @param page the page to get
     * @param pageSize the limit of patient in a page
     * @return the paginated list of patient
     */
    @GET
    public Page<PatientDTO> getPatients(@QueryParam("page") @DefaultValue("0") int page,
                                        @QueryParam("size") @DefaultValue("10") int pageSize) {
        LOGGER.info("Fetching patients on page {}, size {}", page, pageSize);
        return PatientMapper.INSTANCE.toDTO(this.patientService.getPatients(page, pageSize));
    }

    /**
     * Create a new patient
     * @param patientDTO the new patient to create
     * @return A REST Response with the UUID of the new patient
     */
    @POST
    public Response createPatient(@Valid PatientDTO patientDTO) {
        LOGGER.info("Adding patient: {}", patientDTO);
        UUID id = this.patientService.createPatient(PatientMapper.INSTANCE.toPatient(patientDTO));
        return Response.created(URI.create("/patients/" + id.toString())).build();
    }

    /**
     * Get a patient by its UUID
     * @param uuid the patient uuid
     * @return the patient information
     */
    @GET
    @Path("/{uuid}")
    public PatientDTO getPatientById(@PathParam("uuid") UUID uuid) {
        LOGGER.info("Fetching patient: {}", uuid);
        return PatientMapper.INSTANCE.toDTO(this.patientService.getPatientById(uuid));
    }

    /**
     * Update a patient by its UUID
     * Only its name and surname can be updated
     * @param uuid the patient UUID
     * @param patientDTO the updated information the patient
     * @return the updated patient information
     */
    @PUT
    @Path("/{uuid}")
    public PatientDTO updatePatient(@PathParam("uuid") UUID uuid,
                                    @Valid @ConvertGroup(to = ConstraintGroups.Update.class) PatientDTO patientDTO) {
        LOGGER.info("Updating patient: {}", patientDTO);
        if(!uuid.equals(patientDTO.uuid())){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Patient patient = PatientMapper.INSTANCE.toPatient(patientDTO);
        return PatientMapper.INSTANCE.toDTO(this.patientService.updatePatient(patient));
    }

    /**
     * Delete a patient by its UUID
     * @param uuid the patient UUID to delete
     * @return a REST NoContent Response if the patient was deleted
     */
    @DELETE
    @Path("/{uuid}")
    public Response deletePatient(@PathParam("uuid") UUID uuid) {
        LOGGER.info("Deleting patient: {}", uuid);
        this.patientService.deletePatient(uuid);
        return Response.noContent().build();
    }

}
