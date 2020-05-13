package com.assignment.api.service.impl;

import com.assignment.api.service.IUserService;
import com.assignment.api.dao.IUserDao;
import com.assignment.api.error.enums.ErrorEnum;
import com.assignment.api.error.exception.BusinessLogicException;
import com.assignment.api.model.User;
import com.assignment.api.model.dto.UserRegisterDTO;
import com.assignment.api.utils.Constants;
import com.assignment.api.utils.PasswordUtils;
import com.assignment.api.utils.Utils;

import com.google.inject.Inject;
import java.util.Optional;

public class UserServiceImpl implements IUserService {

    @Inject
    IUserDao userDao;

    public User registerUser(UserRegisterDTO userRegisterDTO) {
        Utils.validate(userRegisterDTO);
        if (checkIfEmailExist(userRegisterDTO.getEmail())) {
            throw new BusinessLogicException(ErrorEnum.EMAIL_ALREADY_USED.getCode(), ErrorEnum.EMAIL_ALREADY_USED.getMessage());
        }

        User user = userRegisterDTO.toUserEntity();
        user.setBalance(Constants.DEFAULT_USER_BALANCE);
        prepareSaltAndPasswordHash(user, userRegisterDTO.getPassword().toCharArray());
        userDao.saveOrUpdate(user);
        return user;
    }

    private void prepareSaltAndPasswordHash(User user, char[] password) {
        byte[] salt = PasswordUtils.generateSalt();
        String passwordHash = PasswordUtils.generatePasswordHash(password, salt);
        user.setPasswordSalt(String.valueOf(salt));
        user.setPasswordHash(passwordHash);
    }

    public boolean checkIfEmailExist(String email) {
        if (userDao.findByEmail(email).isPresent()) {
            return true;
        }

        return false;
    }

    public User findUserOrThrowException(Integer userId) {
        Optional<User> userOpt = userDao.find(User.class, userId);
        userOpt.orElseThrow(() -> new BusinessLogicException(ErrorEnum.INVALID_USER.getCode(),
                ErrorEnum.INVALID_USER.getMessage()));
        return (User) userOpt.get();
    }
}
