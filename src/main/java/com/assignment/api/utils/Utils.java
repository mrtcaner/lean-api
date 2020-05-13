package com.assignment.api.utils;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import javax.validation.*;
import java.util.Set;

public class Utils {

    private Utils(){

    }

    public static void validate(Object object){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            System.err.println("Couldn't rollback Transaction:" + ignored);
        }
    }

}
