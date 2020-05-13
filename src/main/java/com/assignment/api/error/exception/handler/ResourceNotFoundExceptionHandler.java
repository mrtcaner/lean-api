package com.assignment.api.error.exception.handler;

import com.assignment.api.error.enums.ErrorEnum;
import com.assignment.api.utils.response.ResponseError;
import com.assignment.api.error.exception.ResourceNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class ResourceNotFoundExceptionHandler
        implements ExceptionMapper<ResourceNotFoundException> {

    public Response toResponse(ResourceNotFoundException ex) {

        ResponseError errorDetails = ResponseError
                .builder()
                .code(ErrorEnum.RESOURCE_NOT_FOUND.getCode())
                .message(ErrorEnum.RESOURCE_NOT_FOUND.getMessage())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(errorDetails).build();
    }
}