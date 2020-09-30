package com.delivery.service;

import com.delivery.entity.City;
import com.delivery.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public City getOrAdd(String cityName) {
        return cityRepository
                .findFirstByName(cityName)
                .orElseGet(() -> add(cityName));
    }

    private City add(String cityName) {
        City city = new City();
        city.setName(cityName);

        return cityRepository.save(city);
    }
}
