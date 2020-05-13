package com.assignment.api.error.exception.handler;

import com.assignment.api.error.exception.BusinessLogicException;
import com.assignment.api.utils.response.ResponseError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class BusinessLogicExceptionHandler
        implements ExceptionMapper<BusinessLogicException> {

    public Response toResponse(BusinessLogicException ex) {
        ResponseError errorDetails = ResponseError
                .builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return Response.status(Response.Status.BAD_REQUEST).entity(errorDetails).build();
    }
}