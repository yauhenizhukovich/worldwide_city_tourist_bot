package com.gmail.yauhenizhukovich.reslivtestapp.repository;

import com.gmail.yauhenizhukovich.reslivtestapp.repository.model.City;

public interface CityRepository {
    City getCityByName(String name);

    void persist(City city);

    City getCityById(Long id);

    void remove(City city);
}
