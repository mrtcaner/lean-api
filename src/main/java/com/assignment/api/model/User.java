package com.assignment.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.assignment.api.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@NamedQueries({
        @NamedQuery(name = "User.findByEmail",
                query = "from User user where user.email= :email"),
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    //@NotBlank(message = "name cannot be empty")
    @Length(max = 255, message = "name cannot be longer than 255 characters")
    private String name;


    @Column
    //@NotBlank(message = "surname cannot be empty")
    @Length(max = 255, message = "name cannot be longer than 255 characters")
    private String surname;

    @Column
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "e-mail address is not valid")
    @Length(max = 255, message = "email cannot be longer than 255 characters")
    private String email;

    @JsonIgnore
    @Column
    private String passwordSalt;

    @JsonIgnore
    @Column
    private String passwordHash;

    @Column
    private double balance;

}
