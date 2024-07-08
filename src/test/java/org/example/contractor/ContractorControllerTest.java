package org.example.contractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getById() throws Exception {
        mockMvc.perform(get("http://localhost:8080/contractor/id_test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id_test"))
                .andExpect(jsonPath("$.name").value("name_test"))
                .andExpect(jsonPath("$.name_full").value("name_full_test"))
                .andExpect(jsonPath("$.inn").value("inn_test"))
                .andExpect(jsonPath("$.ogrn").value("ogrn_test"))
                .andExpect(jsonPath("$.country.id").value("RUS"))
                .andExpect(jsonPath("$.country.name").value("Российская Федерация"))
                .andExpect(jsonPath("$.industry.id").value(1))
                .andExpect(jsonPath("$.industry.name").value("Авиастроение"))
                .andExpect(jsonPath("$.org_form.id").value(1))
                .andExpect(jsonPath("$.org_form.name").value("-"));
    }

    @Test
    public void getContractors() throws Exception {
        SearchContractorRequest request = new SearchContractorRequest();
        request.setFullName("name_full_test");
        request.setInn("inn_test");
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName("Фед");
        request.setCountry(countryDTO);
        IndustryDTO industryDTO = new IndustryDTO();
        industryDTO.setId(1);
        industryDTO.setName("Авиастроение");
        request.setIndustry(industryDTO);

        mockMvc.perform(post("http://localhost:8080/contractor/search")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("id_test"))
                .andExpect(jsonPath("$[0].name").value("name_test"))
                .andExpect(jsonPath("$[0].name_full").value("name_full_test"))
                .andExpect(jsonPath("$[0].inn").value("inn_test"))
                .andExpect(jsonPath("$[0].ogrn").value("ogrn_test"))
                .andExpect(jsonPath("$[0].country.id").value("RUS"))
                .andExpect(jsonPath("$[0].country.name").value("Российская Федерация"))
                .andExpect(jsonPath("$[0].industry.id").value(1))
                .andExpect(jsonPath("$[0].industry.name").value("Авиастроение"))
                .andExpect(jsonPath("$[0].org_form.id").value(1))
                .andExpect(jsonPath("$[0].org_form.name").value("-"));
    }

    @Test
    public void getContractorsSql() throws Exception {
        SearchContractorRequest request = new SearchContractorRequest();
        request.setFullName("name_full_test");
        request.setInn("inn_test");
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName("Фед");
        request.setCountry(countryDTO);
        IndustryDTO industryDTO = new IndustryDTO();
        industryDTO.setId(1);
        industryDTO.setName("Авиастроение");
        request.setIndustry(industryDTO);

        mockMvc.perform(post("http://localhost:8080/contractor/search_sql")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("id_test"))
                .andExpect(jsonPath("$[0].name").value("name_test"))
                .andExpect(jsonPath("$[0].name_full").value("name_full_test"))
                .andExpect(jsonPath("$[0].inn").value("inn_test"))
                .andExpect(jsonPath("$[0].ogrn").value("ogrn_test"))
                .andExpect(jsonPath("$[0].country.id").value("RUS"))
                .andExpect(jsonPath("$[0].country.name").value("Российская Федерация"))
                .andExpect(jsonPath("$[0].industry.id").value(1))
                .andExpect(jsonPath("$[0].industry.name").value("Авиастроение"))
                .andExpect(jsonPath("$[0].org_form.id").value(1))
                .andExpect(jsonPath("$[0].org_form.name").value("-"));
    }

}
