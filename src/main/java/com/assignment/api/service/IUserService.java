package com.assignment.api.service;

import com.assignment.api.model.User;
import com.assignment.api.model.dto.UserRegisterDTO;

public interface IUserService {

    User registerUser(UserRegisterDTO userRegisterDTO);

    User findUserOrThrowException(Integer userId);

}
