package org.example.contractor.controller;

import jakarta.annotation.PostConstruct;
import org.example.auth.entity.User;
import org.example.auth.jwt.JwtUtil;
import org.example.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CountryControllerTest {

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
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostConstruct
    public void postConstruct() {
        User user = userRepository.findByUsernameAndIsActiveTrue("USER").get();
        userAccessToken = "Bearer " + jwtUtil.generateAccessToken(jwtUtil.generateRefreshToken(user), user);
    }

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static String userAccessToken;

    @Test
    public void getAllCountriesTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/country/all")
                        .header(AUTHORIZATION_HEADER, userAccessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("ABH"))
                .andExpect(jsonPath("$[0].name").value("Абхазия"))
                .andExpect(jsonPath("$[1].id").value("ABW"))
                .andExpect(jsonPath("$[1].name").value("Аруба"));
    }

    @Test
    public void getAllCountriesNoAuthorizationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/country/all"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getCountryByIdOkSituationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/country/ABH")
                        .header(AUTHORIZATION_HEADER, userAccessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ABH"))
                .andExpect(jsonPath("$.name").value("Абхазия"));
    }

    @Test
    public void getCountryByIdNoAuthorizationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/country/ABH"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getCountryByIdNotFoundSituationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/country/AAA")
                        .header(AUTHORIZATION_HEADER, userAccessToken))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("не найдена страна с id = AAA"));
    }

}
