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
public class IndustryControllerTest {

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
    public void getAllIndustriesTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/industry/all")
                        .header(AUTHORIZATION_HEADER, userAccessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Авиастроение"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Автомобилестроение"));
    }

    @Test
    public void getAllIndustriesNoAuthorizationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/industry/all"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getIndustryByIdOkSituationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/industry/1")
                        .header(AUTHORIZATION_HEADER, userAccessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Авиастроение"));
    }

    @Test
    public void getIndustryByIdNoAuthorizationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/industry/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getIndustryByIdNotFoundSituationTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/industry/123")
                        .header(AUTHORIZATION_HEADER, userAccessToken))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("не найден индустриальный код с id = 123"));
    }

}
