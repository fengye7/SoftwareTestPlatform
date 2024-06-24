package com.tongji.microservice.teamsphere.userservice.impl;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.userservice.*;
import com.tongji.microservice.teamsphere.userservice.entities.User;
import com.tongji.microservice.teamsphere.userservice.mapper.UserMapper;
import com.tongji.microservice.teamsphere.userservice.util.Jwt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setUsername("newUsername");
        user.setPassword("newPassword");
        user.setEmail("newEmail@example.com");
        user.setAvatar("newAvatarUrl");
    }

    @Test
    void updateUserInfo_success() {
        RegisterRequest request = new RegisterRequest("newUsername","newPassword","newEmail@example.com","newAvatarUrl");

        when(userMapper.selectOne(any())).thenReturn(new User());
        when(userMapper.update(any())).thenReturn(1);

        APIResponse response = userService.updateUserInfo(1, request);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void updateUserInfo_userNotFound() {
        RegisterRequest request = new RegisterRequest("newUsername","newPassword","newEmail@example.com","newAvatarUrl");
        when(userMapper.selectOne(any())).thenReturn(null);

        APIResponse response = userService.updateUserInfo(1, request);

        assertEquals(400, response.getCode());
        assertEquals("用户不存在", response.getMessage());
    }

    @Test
    void getUserInfo_success() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setAvatar("avatarUrl");

        when(userMapper.selectOne(any())).thenReturn(user);

        UserResponse response = userService.getUserInfo(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(1, response.getUserId());
        assertEquals("username", response.getUsername());
        assertEquals("email@example.com", response.getEmail());
        assertEquals("avatarUrl", response.getAvatar());
    }

    @Test
    void getUserInfo_userNotFound() {
        when(userMapper.selectOne(any())).thenReturn(null);

        UserResponse response = userService.getUserInfo(1);

        assertEquals(400, response.getCode());
        assertEquals("用户不存在", response.getMessage());
    }

    @Test
    void deleteUser_success() {
        when(userMapper.selectOne(any())).thenReturn(new User());
        when(userMapper.delete(any())).thenReturn(1);

        APIResponse response = userService.deleteUser(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void deleteUser_userNotFound() {
        when(userMapper.selectOne(any())).thenReturn(null);

        APIResponse response = userService.deleteUser(1);

        assertEquals(400, response.getCode());
        assertEquals("用户不存在", response.getMessage());
    }

    @Test
    void getUserInfoByEmail_success() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setAvatar("avatarUrl");

        when(userMapper.selectOne(any())).thenReturn(user);

        UserResponse response = userService.getUserInfoByEmail("email@example.com");

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(1, response.getUserId());
        assertEquals("username", response.getUsername());
        assertEquals("email@example.com", response.getEmail());
        assertEquals("avatarUrl", response.getAvatar());
    }

    @Test
    void getUserInfoByEmail_userNotFound() {
        when(userMapper.selectOne(any())).thenReturn(null);

        UserResponse response = userService.getUserInfoByEmail("email@example.com");

        assertEquals(400, response.getCode());
        assertEquals("用户不存在", response.getMessage());
    }

    @Test
    void checkID_success() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("password");

        when(userMapper.selectOne(any())).thenReturn(user);

        LoginResponse response = userService.checkID("username", "password");

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void checkID_invalidUsername() {
        when(userMapper.selectOne(any())).thenReturn(null);

        LoginResponse response = userService.checkID("username", "password");

        assertEquals(400, response.getCode());
        assertEquals("无效用户名", response.getMessage());
    }

    @Test
    void checkID_invalidPassword() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("wrongPassword");

        when(userMapper.selectOne(any())).thenReturn(user);

        LoginResponse response = userService.checkID("username", "password");

        assertEquals(400, response.getCode());
        assertEquals("错误的密码", response.getMessage());
    }

    @Test
    void register_success() {
        RegisterRequest request = new RegisterRequest("newUsername", "newPassword", "newEmail@example.com", "newAvatarUrl");

        // 模拟 userMapper 的行为
        when(userMapper.selectOne(any())).thenReturn(null).thenReturn(user);
        when(userMapper.insert(any())).thenReturn(1);

        RegisterResponse response = userService.register(request);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().getId());
        assertEquals("newUsername", response.getData().getUsername());
    }

    @Test
    void register_usernameExists() {
        RegisterRequest request = new RegisterRequest("newUsername","newPassword","newEmail@example.com","newAvatarUrl");

        when(userMapper.selectOne(any())).thenReturn(new User());

        RegisterResponse response = userService.register(request);

        assertEquals(400, response.getCode());
        assertEquals("用户名已存在", response.getMessage());
    }

    @Test
    void register_dbAccessFail() {
        RegisterRequest request = new RegisterRequest("newUsername","newPassword","newEmail@example.com","newAvatarUrl");
        request.setUsername("username");

        when(userMapper.selectOne(any())).thenReturn(null);
        when(userMapper.insert(any())).thenReturn(0);

        RegisterResponse response = userService.register(request);

        assertEquals(400, response.getCode());
        assertEquals("数据库访问失败", response.getMessage());
    }
}