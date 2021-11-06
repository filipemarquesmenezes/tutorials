package com.feign.handling.feignerrorshandling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeignErrorsHandlingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test200() throws Exception {
        mockMvc.perform(get("/hello/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void test404() throws Exception {
        mockMvc.perform(get("/hello/2343242"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
