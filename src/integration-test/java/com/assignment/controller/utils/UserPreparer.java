package com.assignment.controller.utils;

import com.assignment.api.model.dto.UserRegisterDTO;

import java.util.ArrayList;
import java.util.List;

public class UserPreparer {

    public static List<UserRegisterDTO> prepareUserRegisterDtos() {
        List<UserRegisterDTO> users = new ArrayList<>();
        users.add(UserRegisterDTO.builder().name("test1")
                .surname("surname21312").email("test1@yta.com").password("T1.test1").build());
        users.add(UserRegisterDTO.builder().name("test12")
                .surname("surname21312").email("test12@yta.com").password("T1.test12").build());
        users.add(UserRegisterDTO.builder().name("test123")
                .surname("surname21312").email("test123@yta.com").password("T1.test123").build());

        return users;
    }

}
