package org.example.contractor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get("http://localhost:8080/country/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("ABH"))
                .andExpect(jsonPath("$[0].name").value("Абхазия"))
                .andExpect(jsonPath("$[1].id").value("ABW"))
                .andExpect(jsonPath("$[1].name").value("Аруба"));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(get("http://localhost:8080/country/ABH"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ABH"))
                .andExpect(jsonPath("$.name").value("Абхазия"));
    }

}
