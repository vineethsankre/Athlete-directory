package com.example.athletedirectory;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AthleteDirectoryControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Autowired
        private DataSource dataSource;

        private HashMap<Integer, Object[]> countriesHashMap = new HashMap<>(); // Country
        {
                countriesHashMap.put(1, new Object[] { "United States", "https://example.com/us-flag.png" });
                countriesHashMap.put(2, new Object[] { "United Kingdom", "https://example.com/uk-flag.png" });
                countriesHashMap.put(3, new Object[] { "Australia", "https://example.com/au-flag.png" }); // POST
        }

        private HashMap<Integer, Object[]> athletesHashMap = new HashMap<>(); // Athlete
        {
                athletesHashMap.put(1, new Object[] { "Michael Phelps", "Swimming", 1 });
                athletesHashMap.put(2, new Object[] { "Serena Williams", "Tennis", 1 });
                athletesHashMap.put(3, new Object[] { "Mo Farah", "Long-distance running", 2 });
                athletesHashMap.put(4, new Object[] { "Andy Murray", "Tennis", 2 });
                athletesHashMap.put(5, new Object[] { "Steve Smith", "Cricket", 3 }); // POST
                athletesHashMap.put(6, new Object[] { "Ellyse Perry", "Cricket", 3 }); // POST
        }

        @BeforeAll
        public void setupDatabase() {
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                populator.addScripts(new ClassPathResource("/schema.sql"), new ClassPathResource("/data.sql"));
                DatabasePopulatorUtils.execute(populator, dataSource);
        }

        @Test
        @Order(1)
        public void testGetCountries() throws Exception {
                mockMvc.perform(get("/countries")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].countryId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].countryName", Matchers.equalTo(countriesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].flagImageUrl", Matchers.equalTo(countriesHashMap.get(1)[1])))

                                .andExpect(jsonPath("$[1].countryId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].countryName", Matchers.equalTo(countriesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].flagImageUrl", Matchers.equalTo(countriesHashMap.get(2)[1])))

                                .andExpect(jsonPath("$[2].countryId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].countryName", Matchers.equalTo(countriesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].flagImageUrl", Matchers.equalTo(countriesHashMap.get(3)[1])));
        }

        @Test
        @Order(2)
        public void testGetAthletes() throws Exception {
                mockMvc.perform(get("/countries/athletes")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(6)))

                                .andExpect(jsonPath("$[0].athleteId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].athleteName", Matchers.equalTo(athletesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].sport", Matchers.equalTo(athletesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(1)[2])))

                                .andExpect(jsonPath("$[1].athleteId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].athleteName", Matchers.equalTo(athletesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].sport", Matchers.equalTo(athletesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(2)[2])))

                                .andExpect(jsonPath("$[2].athleteId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].athleteName", Matchers.equalTo(athletesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].sport", Matchers.equalTo(athletesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(3)[2])))

                                .andExpect(jsonPath("$[3].athleteId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].athleteName", Matchers.equalTo(athletesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].sport", Matchers.equalTo(athletesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(4)[2])))

                                .andExpect(jsonPath("$[4].athleteId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].athleteName", Matchers.equalTo(athletesHashMap.get(5)[0])))
                                .andExpect(jsonPath("$[4].sport", Matchers.equalTo(athletesHashMap.get(5)[1])))
                                .andExpect(jsonPath("$[4].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(5)[2])))

                                .andExpect(jsonPath("$[5].athleteId", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].athleteName", Matchers.equalTo(athletesHashMap.get(6)[0])))
                                .andExpect(jsonPath("$[5].sport", Matchers.equalTo(athletesHashMap.get(6)[1])))
                                .andExpect(jsonPath("$[5].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(6)[2])));
        }

        @Test
        @Order(3)
        public void testGetCountryAthletes() throws Exception {
                mockMvc.perform(get("/countries/1/athletes")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(2)))

                                .andExpect(jsonPath("$[*].athleteId", hasItem(1)))
                                .andExpect(jsonPath("$[*].athleteId", hasItem(2)));

                mockMvc.perform(get("/countries/2/athletes")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(2)))

                                .andExpect(jsonPath("$[*].athleteId", hasItem(3)))
                                .andExpect(jsonPath("$[*].athleteId", hasItem(4)));

                mockMvc.perform(get("/countries/3/athletes")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(2)))

                                .andExpect(jsonPath("$[*].athleteId", hasItem(5)))
                                .andExpect(jsonPath("$[*].athleteId", hasItem(6)));
        }

        @Test
        @Order(4)
        public void testGetAthleteCountry() throws Exception {
                mockMvc.perform(get("/athletes/1/country")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.countryId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.countryName", Matchers.equalTo(countriesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.flagImageUrl", Matchers.equalTo(countriesHashMap.get(1)[1])));

                mockMvc.perform(get("/athletes/2/country")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.countryId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.countryName", Matchers.equalTo(countriesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.flagImageUrl", Matchers.equalTo(countriesHashMap.get(1)[1])));

                mockMvc.perform(get("/athletes/3/country")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.countryId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.countryName", Matchers.equalTo(countriesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.flagImageUrl", Matchers.equalTo(countriesHashMap.get(2)[1])));

                mockMvc.perform(get("/athletes/4/country")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.countryId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.countryName", Matchers.equalTo(countriesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.flagImageUrl", Matchers.equalTo(countriesHashMap.get(2)[1])));

                mockMvc.perform(get("/athletes/5/country")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.countryId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.countryName", Matchers.equalTo(countriesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.flagImageUrl", Matchers.equalTo(countriesHashMap.get(3)[1])));

                mockMvc.perform(get("/athletes/6/country")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.countryId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.countryName", Matchers.equalTo(countriesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.flagImageUrl", Matchers.equalTo(countriesHashMap.get(3)[1])));
        }

        @Test
        @Order(5)
        public void testDeleteAthleteNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/countries/athletes/48")
                                .contentType(MediaType.APPLICATION_JSON);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(6)
        public void testDeleteAthlete() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/countries/athletes/6")
                                .contentType(MediaType.APPLICATION_JSON);

                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(7)
        public void testAfterDeleteAthlete() throws Exception {
                mockMvc.perform(get("/countries/athletes")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(5)))

                                .andExpect(jsonPath("$[0].athleteId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].athleteName", Matchers.equalTo(athletesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].sport", Matchers.equalTo(athletesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(1)[2])))

                                .andExpect(jsonPath("$[1].athleteId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].athleteName", Matchers.equalTo(athletesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].sport", Matchers.equalTo(athletesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(2)[2])))

                                .andExpect(jsonPath("$[2].athleteId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].athleteName", Matchers.equalTo(athletesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].sport", Matchers.equalTo(athletesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(3)[2])))

                                .andExpect(jsonPath("$[3].athleteId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].athleteName", Matchers.equalTo(athletesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$[3].sport", Matchers.equalTo(athletesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$[3].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(4)[2])))

                                .andExpect(jsonPath("$[4].athleteId", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].athleteName", Matchers.equalTo(athletesHashMap.get(5)[0])))
                                .andExpect(jsonPath("$[4].sport", Matchers.equalTo(athletesHashMap.get(5)[1])))
                                .andExpect(jsonPath("$[4].country.countryId",
                                                Matchers.equalTo(athletesHashMap.get(5)[2])));
        }

        @Test
        @Order(8)
        public void testDeleteCountryNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/countries/48")
                                .contentType(MediaType.APPLICATION_JSON);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(9)
        public void testDeleteCountry() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/countries/2")
                                .contentType(MediaType.APPLICATION_JSON);

                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(10)
        public void testAfterDeleteCountry() throws Exception {
                mockMvc.perform(get("/countries")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(2)))

                                .andExpect(jsonPath("$[0].countryId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].countryName", Matchers.equalTo(countriesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].flagImageUrl", Matchers.equalTo(countriesHashMap.get(1)[1])))

                                .andExpect(jsonPath("$[1].countryId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[1].countryName", Matchers.equalTo(countriesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[1].flagImageUrl", Matchers.equalTo(countriesHashMap.get(3)[1])));

                mockMvc.perform(get("/countries/athletes/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.athleteId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.athleteName", Matchers.equalTo(athletesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.sport", Matchers.equalTo(athletesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.country.countryId").doesNotExist());

                mockMvc.perform(get("/countries/athletes/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.athleteId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.athleteName", Matchers.equalTo(athletesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.sport", Matchers.equalTo(athletesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.country.countryId").doesNotExist());
        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table athlete");
                jdbcTemplate.execute("drop table country");
        }

}