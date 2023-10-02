package com.practice.sns.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 회원가입() {
        String userName = "user1";
        String password = "password";

        mockMvc.perform(post("/api/v1/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content()
        ).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void 회원가입시_이미_회원가입된_userName일_경우_에러반환() {
        String userName = "user1";
        String password = "password";

        mockMvc.perform(post("/api/v1/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content()
                ).andDo(print())
                .andExpect(status().isConflict());
    }
}
