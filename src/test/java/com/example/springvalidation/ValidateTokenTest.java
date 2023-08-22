package com.example.springvalidation;

import com.example.springvalidation.model.SignupRequest;
import com.example.springvalidation.util.JWTUtility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ValidateTokenTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtility utility;


    @Test
    public void validTest() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwb3BzaSIsImV4cCI6MTY5NDgwMDMyOCwiaWF0IjoxNjkyNjQwMzI4fQ.CyZKz9upUlH9QHkCql9AvoRDiL6kEhgW5gS2jSNtsvy80RzOKK9NS7PKgOKezktUTRD68jJ2CuqJlAS3KGyMUw";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("steven@gmail.com");
        signupRequest.setPassword("password");
        signupRequest.setUsername("steven");
        signupRequest.setDateOfBirth(new Date());

        given(utility.getUsernameFromToken(token)).willReturn(signupRequest.getUsername());

        given(utility.validateToken(token, signupRequest.getUsername())).willReturn(true);

        ResultActions response = mockMvc.perform(get("/validate-token/{token}", token));

        response.andExpect(status().isOk());
    }

    @Test
    public void inValidTest() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwb3BzaSIsImV4cCI6MTY5NDgwMDMyOCwiaWF0IjoxNjkyNjQwMzI4fQ.CyZKz9upUlH9QHkCql9AvoRDiL6kEhgW5gS2jSNtsvy80RzOKK9NS7PKgOKezktUTRD68jJ2CuqJlAS3KGyMUw";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("steven@gmail.com");
        signupRequest.setPassword("password");
        signupRequest.setUsername("steven");
        signupRequest.setDateOfBirth(new Date());

        given(utility.getUsernameFromToken(token)).willReturn(signupRequest.getUsername());

        given(utility.validateToken(token, signupRequest.getUsername())).willReturn(false);

        ResultActions response = mockMvc.perform(get("/validate-token/{token}", token));

        response.andExpect(status().isBadRequest());
    }

}