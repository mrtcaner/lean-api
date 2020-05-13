package com.assignment.api.utils;

public class Constants {

    public static final String BASE_PACKAGE = "com.assignment.api";

    public static final Double DEFAULT_SEARCH_DIAMETER_IN_KM = 1.0;

    public static final Double DEFAULT_RENT_COST = 100.0;

    public static final Double DEFAULT_USER_BALANCE = 1000.0;

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /*
        (?=.*[a-z])     : This matches the presence of at least one lowercase letter.
        (?=.*\\d)         : This matches the presence of at least one digit i.e. 0-9.
        (?=.*[@#$%!.])    : This matches the presence of at least one special character.
        ((?=.*[A-Z])    : This matches the presence of at least one capital letter.
        {6,12}          : This limits the length of password from minimum 6 letters to maximum 16 letters.
     */
    public static final String PASSWORD_REGEX = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!.]).{6,12})";

    private Constants(){

    }


}
