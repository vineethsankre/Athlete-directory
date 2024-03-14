package com.example.athletedirectory;

import com.example.athletedirectory.controller.AthleteController;
import com.example.athletedirectory.controller.CountryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private AthleteController athleteController;

    @Autowired
    private CountryController countryController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(athleteController).isNotNull();
        assertThat(countryController).isNotNull();
    }
}
