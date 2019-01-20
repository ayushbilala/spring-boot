package com.paypalinterview.problem4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypalinterview.problem4.model.User;
import com.paypalinterview.problem4.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private JacksonTester<User> jsonTester;

    private User user;

    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
        user = new User("SomeUserId", "SomeUserAsset");
        String[] ipAddress = {"127.0.0.1", "SomeOtherIpAddress"};
        user.setIpAddress(new HashSet<>(Arrays.asList(ipAddress)));
    }

    @Test
    public void addUser() throws Exception {
        final String userJson = jsonTester.write(user).getJson();
        given(userService.isValid(any(User.class))).willReturn(true);

        mockMvc
                .perform(post("/addUser").content(userJson).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        verify(userService).addUser(any(User.class), any(LocalTime.class), anyString());
    }

    @Test
    public void getUser() throws Exception {
        final String userJson = jsonTester.write(user).getJson();
        given(userService.isValid(any(User.class))).willReturn(true);
        given(userService.getUser(user.getUserId())).willReturn(user);

        mockMvc
                .perform(post("/addUser").content(userJson).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        final ResultActions result = mockMvc.perform(get("/getUser/SomeUserId"));
        result.andExpect(status().isOk());
        verifyJson(result);
    }

    @Test
    public void detectAnomaly() throws Exception {
        final String userJson = jsonTester.write(user).getJson();
        given(userService.isValid(any(User.class))).willReturn(true);
        given(userService.getUser(user.getUserId())).willReturn(user);
        given(userService.detectAnomaly(user.getUserId(), "SomeOtherIpAddress")).willReturn(true);

        mockMvc
                .perform(post("/addUser").content(userJson).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        final ResultActions result = mockMvc.perform(get("/detectAnomaly/SomeUserId/SomeOtherIpAddress"));
        result.andExpect(status().isOk());
        result.andExpect(content().string(String.valueOf(true)));
    }

    private void verifyJson(final ResultActions action) throws Exception {
        action
                .andExpect(jsonPath("userId", is(user.getUserId())))
                .andExpect(jsonPath("userAsset", is(user.getUserAsset())))
                .andExpect(jsonPath("loginTime", is(user.getLoginTime())));
    }
}