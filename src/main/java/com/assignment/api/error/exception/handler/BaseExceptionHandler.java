package com.assignment.api.error.exception.handler;

import com.assignment.api.error.enums.ErrorEnum;
import com.assignment.api.utils.response.ResponseError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class BaseExceptionHandler
        implements ExceptionMapper<Exception> {

    public Response toResponse(Exception ex) {
        ResponseError errorDetails = ResponseError
                .builder()
                .code(ErrorEnum.GENERIC_EXCEPTION.getCode())
                .message(ErrorEnum.GENERIC_EXCEPTION.getMessage())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        ex.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorDetails).build();
    }
}