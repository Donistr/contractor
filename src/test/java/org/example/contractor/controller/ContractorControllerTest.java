package org.example.contractor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.SetMainBorrowerDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ContractorControllerTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", postgreSQLContainer::getJdbcUrl);
        registry.add("DB_USERNAME", postgreSQLContainer::getUsername);
        registry.add("DB_PASSWORD", postgreSQLContainer::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getContractorByIdOkSituationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/contractor/id_1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id_1"))
                .andExpect(jsonPath("$.name").value("name_1"))
                .andExpect(jsonPath("$.name_full").value("name_full_1"))
                .andExpect(jsonPath("$.inn").value("inn_1"))
                .andExpect(jsonPath("$.ogrn").value("ogrn_1"))
                .andExpect(jsonPath("$.country.id").value("ABH"))
                .andExpect(jsonPath("$.country.name").value("Абхазия"))
                .andExpect(jsonPath("$.industry.id").value(1))
                .andExpect(jsonPath("$.industry.name").value("Авиастроение"))
                .andExpect(jsonPath("$.org_form.id").value(2))
                .andExpect(jsonPath("$.org_form.name").value("Автономная некоммерческая организация"));
    }

    @Test
    public void getContractorByIdNotFoundSituationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/contractor/id_123"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("не найден контрагент с id = id_123"));
    }

    @Test
    public void searchContractorsTest() throws Exception {
        SearchContractorRequest request = new SearchContractorRequest();
        request.setFullName("name_full_2");
        request.setInn("inn_2");
        CountryDTO countryDTO = CountryDTO.builder()
                .name("руб")
                .build();
        request.setCountry(countryDTO);
        IndustryDTO industryDTO = IndustryDTO.builder()
                .id(2)
                .name("Автомобилестроение")
                .build();
        request.setIndustry(industryDTO);

        mockMvc.perform(post("http://localhost:8080/contractor/search")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("id_2"))
                .andExpect(jsonPath("$[0].name").value("name_2"))
                .andExpect(jsonPath("$[0].name_full").value("name_full_2"))
                .andExpect(jsonPath("$[0].inn").value("inn_2"))
                .andExpect(jsonPath("$[0].ogrn").value("ogrn_2"))
                .andExpect(jsonPath("$[0].country.id").value("ABW"))
                .andExpect(jsonPath("$[0].country.name").value("Аруба"))
                .andExpect(jsonPath("$[0].industry.id").value(2))
                .andExpect(jsonPath("$[0].industry.name").value("Автомобилестроение"))
                .andExpect(jsonPath("$[0].org_form.id").value(3))
                .andExpect(jsonPath("$[0].org_form.name").value("Автономное учреждение"));
    }

    @Test
    public void searchContractorsSqlTest() throws Exception {
        SearchContractorRequest request = new SearchContractorRequest();
        request.setFullName("name_full_1");
        request.setInn("inn_1");
        CountryDTO countryDTO = CountryDTO.builder()
                .name("хаз")
                .build();
        request.setCountry(countryDTO);
        IndustryDTO industryDTO = IndustryDTO.builder()
                .id(1)
                .name("Авиастроение")
                .build();
        request.setIndustry(industryDTO);

        mockMvc.perform(post("http://localhost:8080/contractor/search_sql")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("id_1"))
                .andExpect(jsonPath("$[0].name").value("name_1"))
                .andExpect(jsonPath("$[0].name_full").value("name_full_1"))
                .andExpect(jsonPath("$[0].inn").value("inn_1"))
                .andExpect(jsonPath("$[0].ogrn").value("ogrn_1"))
                .andExpect(jsonPath("$[0].country.id").value("ABH"))
                .andExpect(jsonPath("$[0].country.name").value("Абхазия"))
                .andExpect(jsonPath("$[0].industry.id").value(1))
                .andExpect(jsonPath("$[0].industry.name").value("Авиастроение"))
                .andExpect(jsonPath("$[0].org_form.id").value(2))
                .andExpect(jsonPath("$[0].org_form.name").value("Автономная некоммерческая организация"));
    }

    @Test
    @DirtiesContext
    public void setMainBorrowerTest() throws Exception {
        SetMainBorrowerDTO request = SetMainBorrowerDTO.builder()
                .id("id_1")
                .activeMainBorrower(false)
                .build();
        mockMvc.perform(patch("http://localhost:8080/contractor/main-borrower")
                    .content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id_1"))
                .andExpect(jsonPath("$.name").value("name_1"))
                .andExpect(jsonPath("$.name_full").value("name_full_1"))
                .andExpect(jsonPath("$.inn").value("inn_1"))
                .andExpect(jsonPath("$.ogrn").value("ogrn_1"))
                .andExpect(jsonPath("$.country.id").value("ABH"))
                .andExpect(jsonPath("$.country.name").value("Абхазия"))
                .andExpect(jsonPath("$.industry.id").value(1))
                .andExpect(jsonPath("$.industry.name").value("Авиастроение"))
                .andExpect(jsonPath("$.org_form.id").value(2))
                .andExpect(jsonPath("$.org_form.name").value("Автономная некоммерческая организация"))
                .andExpect(jsonPath("$.active_main_borrower").value(false));

        request = SetMainBorrowerDTO.builder()
                .id("id_1")
                .activeMainBorrower(true)
                .build();
        mockMvc.perform(patch("http://localhost:8080/contractor/main-borrower")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id_1"))
                .andExpect(jsonPath("$.name").value("name_1"))
                .andExpect(jsonPath("$.name_full").value("name_full_1"))
                .andExpect(jsonPath("$.inn").value("inn_1"))
                .andExpect(jsonPath("$.ogrn").value("ogrn_1"))
                .andExpect(jsonPath("$.country.id").value("ABH"))
                .andExpect(jsonPath("$.country.name").value("Абхазия"))
                .andExpect(jsonPath("$.industry.id").value(1))
                .andExpect(jsonPath("$.industry.name").value("Авиастроение"))
                .andExpect(jsonPath("$.org_form.id").value(2))
                .andExpect(jsonPath("$.org_form.name").value("Автономная некоммерческая организация"))
                .andExpect(jsonPath("$.active_main_borrower").value(true));
    }

}
