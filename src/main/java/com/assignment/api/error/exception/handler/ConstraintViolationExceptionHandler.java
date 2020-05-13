package com.assignment.api.error.exception.handler;

import com.assignment.api.error.enums.ErrorEnum;
import com.assignment.api.utils.response.ResponseError;
import org.hibernate.exception.ConstraintViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.time.LocalDateTime;

public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    public Response toResponse(ConstraintViolationException ex) {
        ResponseError errorDetails = ResponseError
                .builder()
                .code(ErrorEnum.INVALID_DATA.getCode())
                .message(ex.getMessage())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return Response.status(Response.Status.BAD_REQUEST).entity(errorDetails).build();
    }
}
