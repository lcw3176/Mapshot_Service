package com.joebrooks.mapshotservice.global.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class HttpRequestFilterTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

    }

    @Test
    void post_요청_방지_테스트() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void delete_요청_방지_테스트() throws Exception {
        mockMvc.perform(delete("/manual"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void put_요청_방지_테스트() throws Exception {
        mockMvc.perform(put("/contact"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void patch_요청_방지_테스트() throws Exception {
        mockMvc.perform(patch("/notice"))
                .andExpect(status().is4xxClientError());
    }
}