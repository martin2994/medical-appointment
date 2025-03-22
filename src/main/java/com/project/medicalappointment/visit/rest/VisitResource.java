package com.project.medicalappointment.visit.rest;

import com.project.medicalappointment.common.model.Page;
import com.project.medicalappointment.common.validator.ConstraintGroups;
import com.project.medicalappointment.visit.application.VisitService;
import com.project.medicalappointment.visit.application.model.Visit;
import com.project.medicalappointment.visit.rest.mapper.VisitMapper;
import com.project.medicalappointment.visit.rest.model.VisitDTO;
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
 * REST Controller class which defines all endpoints to handle CRUD operation on visit
 */
@Path("/visits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitResource.class);

    private final VisitService visitService;

    public VisitResource(VisitService visitService) {
        this.visitService = visitService;
    }

    /**
     * Create a new visit
     * @param visitDTO the new visit to create
     * @return A REST Response with the UUID of the new visit
     */
    @POST
    public Response createVisit(@Valid VisitDTO visitDTO) {
        LOGGER.info("Creating visit for patient: {}", visitDTO.patientUUID());
        UUID id = visitService.createVisit(VisitMapper.INSTANCE.toVisit(visitDTO));
        return Response.created(URI.create("/visits/" + id)).build();
    }

    /**
     * Get a paginated list of visit
     * @param page the page to get
     * @param pageSize the limit of visit in a page
     * @return the paginated list of visit
     */
    @GET
    public Page<VisitDTO> getAllVisits(@QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("size") @DefaultValue("10") int pageSize) {
        LOGGER.info("Fetching visits on page {}, size {}", page, pageSize);
        return VisitMapper.INSTANCE.toDTO(visitService.getVisits(page, pageSize));
    }

    /**
     * Get a visit by its UUID
     * @param uuid the visit uuid
     * @return the visit information
     */
    @GET
    @Path("/{uuid}")
    public VisitDTO getVisitById(@PathParam("uuid") UUID uuid) {
        LOGGER.info("Fetching visit: {}", uuid);
        return VisitMapper.INSTANCE.toDTO(visitService.getVisitById(uuid));
    }

    /**
     * Update a visit by its UUID
     * Only its name and surname can be updated
     * @param uuid the visit UUID
     * @param visitDTO the updated information the visit
     * @return the updated visit information
     */
    @PUT
    @Path("/{uuid}")
    public VisitDTO updateVisit(@PathParam("uuid") UUID uuid,
                                @Valid @ConvertGroup(to = ConstraintGroups.Update.class) VisitDTO visitDTO) {
        LOGGER.info("Updating visit: {}", uuid);
        if (!uuid.equals(visitDTO.uuid())) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        Visit visit = VisitMapper.INSTANCE.toVisit(visitDTO);
        return VisitMapper.INSTANCE.toDTO(this.visitService.updateVisit(visit));
    }

    /**
     * Delete a visit by its UUID
     * @param uuid the visit UUID to delete
     * @return a REST noContent Response if the visit was deleted
     */
    @DELETE
    @Path("/{uuid}")
    public Response deleteVisit(@PathParam("uuid") UUID uuid) {
        LOGGER.info("Deleting visit: {}", uuid);
        visitService.deleteVisit(uuid);
        return Response.noContent().build();
    }
}

