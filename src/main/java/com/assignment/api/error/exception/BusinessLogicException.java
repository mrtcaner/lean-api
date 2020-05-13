package com.assignment.api.error.exception;

import lombok.Data;

@Data
public class BusinessLogicException extends RuntimeException {

    private int errorCode;


    public BusinessLogicException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }

    public BusinessLogicException(int errorCode, String errorMessage, Throwable err) {
        super(errorMessage, err);
        this.errorCode = errorCode;
    }


}
