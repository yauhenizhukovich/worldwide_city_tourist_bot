package com.gmail.yauhenizhukovich.reslivtestapp.service;

import com.gmail.yauhenizhukovich.reslivtestapp.service.exception.CityExistenceException;
import com.gmail.yauhenizhukovich.reslivtestapp.service.model.CityDTO;
import com.gmail.yauhenizhukovich.reslivtestapp.service.model.UpdateCityDTO;

public interface CityService {
    CityDTO getCityByName(String name) throws CityExistenceException;

    void addCity(CityDTO city) throws CityExistenceException;

    void deleteCityByName(String name) throws CityExistenceException;

    void updateCityByName(UpdateCityDTO city, String name) throws CityExistenceException;
}
