package moe.pine.izetta.controllers;

import lombok.SneakyThrows;
import moe.pine.izetta.properties.AppProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HealthControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppProperties appProperties;

    @Test
    @SneakyThrows
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void homeTest() {
        when(appProperties.getSiteUrl()).thenReturn("https://www.example.com");

        mvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("https://www.example.com"));

        verify(appProperties).getSiteUrl();
    }

    @Test
    @SneakyThrows
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void homeTest_notFound() {
        when(appProperties.getSiteUrl()).thenReturn(null);

        mvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().is(HttpStatus.NOT_FOUND.value()));

        verify(appProperties).getSiteUrl();
    }

    @Test
    @SneakyThrows
    public void healthTest() {
        mvc.perform(MockMvcRequestBuilders.get("/health"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("OK")));
    }
}
