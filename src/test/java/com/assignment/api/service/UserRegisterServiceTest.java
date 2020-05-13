package com.assignment.api.service;

import com.assignment.api.service.impl.UserServiceImpl;
import com.assignment.api.dao.impl.UserDaoImpl;
import com.assignment.api.error.exception.BusinessLogicException;
import com.assignment.api.model.User;
import com.assignment.api.model.dto.UserRegisterDTO;
import com.assignment.api.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterServiceTest {

    @Mock
    private UserDaoImpl userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegisterDTO userRegisterDTO;


    @Before
    public void setup() {
        userRegisterDTO = UserRegisterDTO.builder().name("name").surname("surname").email("asd@asd.com")
                .password("T123.a")
                .build();
    }

    @Test
    public void userRegister_whenGivenValidUserRegister_returnSavedUser() {
        when(userDao.saveOrUpdate(any(User.class))).then(returnsFirstArg());
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        User user = userService.registerUser(userRegisterDTO);
        assertNotNull(user);
        assertNotNull(user.getPasswordSalt());
        assertNotNull(user.getPasswordHash());
        assertNotNull(user.getBalance());
        assertTrue(Double.valueOf(user.getBalance()).equals(Constants.DEFAULT_USER_BALANCE));
    }

    @Test(expected = BusinessLogicException.class)
    public void userRegister_whenGivenExistingEmailAddress_throwsBusinessLogicException() {
        when(userDao.findByEmail(any())).thenReturn(java.util.Optional.of(new User()));
        userService.registerUser(userRegisterDTO);
    }
}
