package com.example.athletedirectory.service;

import com.example.athletedirectory.model.Athlete;
import com.example.athletedirectory.model.Country;
import com.example.athletedirectory.repository.AthleteJpaRepository;
import com.example.athletedirectory.repository.CountryJpaRepository;
import com.example.athletedirectory.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryJpaService implements CountryRepository {

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Autowired
    private AthleteJpaRepository athleteJpaRepository;

    @Override
    public ArrayList<Country> getCountries() {
        List<Country> countryList = countryJpaRepository.findAll();
        ArrayList<Country> countries = new ArrayList<>(countryList);
        return countries;
    }

    @Override
    public Country getCountryById(int countryId) {
        try {
            return countryJpaRepository.findById(countryId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Country addCountry(Country country) {
        return countryJpaRepository.save(country);
    }

    @Override
    public Country updateCountry(int countryId, Country country) {
        try {
            Country newCountry = countryJpaRepository.findById(countryId).get();
            if (country.getCountryName() != null) {
                newCountry.setCountryName(country.getCountryName());
            }
            if (country.getFlagImageUrl() != null) {
                newCountry.setFlagImageUrl(country.getFlagImageUrl());
            }
            return countryJpaRepository.save(newCountry);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCountry(int countryId) {
        try {
            Country country = countryJpaRepository.findById(countryId).get();
            List<Athlete> athleteList = athleteJpaRepository.findByCountry(country);
            for (Athlete athlete : athleteList) {
                athlete.setCountry(null);
            }
            athleteJpaRepository.saveAll(athleteList);
            countryJpaRepository.deleteById(countryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public List<Athlete> getCountryAthletes(int countryId) {
        try {
            Country country = countryJpaRepository.findById(countryId).get();
            return athleteJpaRepository.findByCountry(country);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}