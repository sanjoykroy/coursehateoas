package com.realtech.coursehateoas.course;


import com.realtech.coursehateoas.course.web.IndexController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.web.servlet.View;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

public class IndexControllerTest {

    @InjectMocks
    private IndexController indexController;
    @Mock
    private View mockView;
    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        indexController = new IndexController();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(indexController).build();
    }

    @Test
    public void shouldReturnRootIndex() throws Exception{
        this.mockMvc.perform(get("/index")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
