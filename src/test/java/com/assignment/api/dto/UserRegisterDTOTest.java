package com.assignment.api.dto;

import com.assignment.api.model.dto.UserRegisterDTO;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UserRegisterDTOTest {

    private Validator validator;

    private UserRegisterDTO userRegisterDTO;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        userRegisterDTO = UserRegisterDTO.builder().name("name").surname("surname").email("asd@asd.com")
                .password("T123.a")
                .build();

    }

    @Test
    public void validate_whenGivenValid_thenValidated() {
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validate_whenEmptyName_thenInvalidated() {
        userRegisterDTO.setName("");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validate_whenNullName_thenInvalidated() {
        userRegisterDTO.setName(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validate_whenEmptySurname_thenInvalidated() {
        userRegisterDTO.setSurname("");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validate_whenNullSurname_thenInvalidated() {
        userRegisterDTO.setSurname(null);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validate_whenFormedEmail_thenValidated() {
        userRegisterDTO.setEmail("sadsd@test.com");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validate_whenMalformedEmail_thenInvalidated() {
        userRegisterDTO.setEmail("ASA@.COM");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validate_whenFormedPassword_thenValidated() {
        userRegisterDTO.setPassword("T1.abcd");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validate_whenPasswordWithoutUppercaseLetter_thenInvalidated() {
        userRegisterDTO.setPassword("a1.abcd");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validate_whenPasswordWithoutSpecialCharater_thenInvalidated() {
        userRegisterDTO.setPassword("T1abcd2");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validate_whenPasswordWithoutLowercaseLetter_thenInvalidated() {
        userRegisterDTO.setPassword("T1.1111");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validate_whenPasswordLengthSmallerThan6_thenInvalidated() {
        userRegisterDTO.setPassword("T1.11");
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(userRegisterDTO);
        assertFalse(violations.isEmpty());
    }

}
