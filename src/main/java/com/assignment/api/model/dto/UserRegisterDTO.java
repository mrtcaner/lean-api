package com.assignment.api.model.dto;

import com.assignment.api.model.User;
import com.assignment.api.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotBlank(message = "name cannot be empty")
    @Length(max = 255, message = "name cannot be longer than 255 characters")
    private String name;


    @NotBlank(message = "surname cannot be empty")
    @Length(max = 255, message = "name cannot be longer than 255 characters")
    private String surname;

    @NotBlank(message = "email cannot be empty")
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "e-mail address is not valid")
    @Length(max = 255, message = "email cannot be longer than 255 characters")
    private String email;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "least one lowercase letter, " +
            "least one digit, " +
            "least one special character, " +
            "least one capital letter, " +
            "minimum 6 letters to maximum 16 letters")
    @NotBlank(message = "password cannot be empty")
    private String password;

    public User toUserEntity() {
        return User.builder().name(name).surname(surname).email(email).build();
    }
}
