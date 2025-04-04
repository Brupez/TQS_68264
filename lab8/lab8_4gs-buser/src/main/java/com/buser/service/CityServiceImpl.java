package com.buser.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.buser.model.City;
import com.buser.repository.CityRepository;

public class CityServiceImpl implements CityService {

    private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityRepository cityRepository;

    @Override
    public City getCityById(Long id) {
        log.info("Fetching city with ID: {}", id);
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            return city.get();
        } else {
            log.warn("City not found with ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found");
        }
    }

    

    
}
