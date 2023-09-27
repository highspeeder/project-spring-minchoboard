package com.cos.blog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserContorller.class)
public class UserContorllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void joinForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/joinForm"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/loginForm"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void updateForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/updateForm"))
                .andExpect(status().is3xxRedirection());
    }
}