package com.assignment.api.error.enums;

public enum ErrorEnum {

    GENERIC_EXCEPTION("Error",1000),
    INVALID_DATA("Invalid Data",1001),
    RESOURCE_NOT_FOUND("Resource not found",1002),

    EMAIL_ALREADY_USED("Email address is already in use",1006),
    CANNOT_BLOCK_MORE_THAN_ONE("Already blocked a car. Must unblock before block a new one",1007),
    CAR_NO_LONGER_AVAILABLE("Car is no longer available",1008),
    INVALID_USER("Invalid user",1009),
    RENT_ALREADY_STARTED("Rent already started",1010),
    USER_NO_BLOCKED_CAR("User doesn't have a blocked car",1011),
    NO_SUCH_RENT_ALREADY_ENDED("No such rent or rent already ended",1012);


    private String message;
    private Integer code;

    ErrorEnum(String message, int code){
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
