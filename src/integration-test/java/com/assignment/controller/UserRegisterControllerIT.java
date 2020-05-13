package com.assignment.controller;

import com.assignment.controller.utils.UserPreparer;
import com.assignment.api.model.User;
import com.assignment.api.model.dto.UserRegisterDTO;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserRegisterControllerIT extends AbstractBaseControllerIT {

    @Test
    public void userRegister_whenGivenValidUser_thenReturnsUserWith201() {
        UserRegisterDTO registerDTO = UserPreparer.prepareUniqueUser();

        Response response = saveUser(registerDTO);
        assertEquals(HttpStatus.CREATED_201, response.getStatus());

        User user = response.readEntity(User.class);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(user.getName(), registerDTO.getName());
        assertEquals(user.getSurname(), registerDTO.getSurname());
    }
}
