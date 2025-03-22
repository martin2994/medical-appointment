package com.project.medicalappointment.common.exception;

import io.quarkus.arc.ArcUndeclaredThrowableException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

/**
 * Class which handles all common exceptions to raise a valid error response
 */
public class ExceptionMappers {

    @ServerExceptionMapper
    public Response serverExceptionMapper(ArcUndeclaredThrowableException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }

}
