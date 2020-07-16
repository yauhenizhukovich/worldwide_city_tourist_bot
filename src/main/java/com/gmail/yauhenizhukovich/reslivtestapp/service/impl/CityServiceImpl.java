package com.gmail.yauhenizhukovich.reslivtestapp.service.impl;

import com.gmail.yauhenizhukovich.reslivtestapp.repository.CityRepository;
import com.gmail.yauhenizhukovich.reslivtestapp.repository.model.City;
import com.gmail.yauhenizhukovich.reslivtestapp.service.CityService;
import com.gmail.yauhenizhukovich.reslivtestapp.service.exception.CityExistenceException;
import com.gmail.yauhenizhukovich.reslivtestapp.service.model.CityDTO;
import com.gmail.yauhenizhukovich.reslivtestapp.service.model.UpdateCityDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CityServiceImpl implements CityService {
    public static final String CITY_EXISTENCE_EXCEPTION = "City with this name already exists.";
    public static final String CITY_NONEXISTENCE_EXCEPTION = "City with this name doesnt exist.";
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityDTO getCityByName(String name) throws CityExistenceException {
        City city = cityRepository.getCityByName(name);
        checkCityForNotNull(city);
        return convertDatabaseObjectToDTO(city);
    }

    @Override
    public void addCity(CityDTO cityDTO) throws CityExistenceException {
        String name = cityDTO.getName();
        if (cityRepository.getCityByName(name) != null) {
            throw new CityExistenceException(CITY_EXISTENCE_EXCEPTION);
        }
        City city = convertDTOToDatabaseObject(cityDTO);
        cityRepository.persist(city);
    }

    @Override
    public void deleteCityByName(String name) throws CityExistenceException {
        City city = cityRepository.getCityByName(name);
        checkCityForNotNull(city);
        cityRepository.remove(city);
    }

    @Override
    public void updateCityByName(UpdateCityDTO cityDTO, String name) throws CityExistenceException {
        City city = cityRepository.getCityByName(name);
        checkCityForNotNull(city);
        if (cityDTO.getName() != null) {
            city.setName(cityDTO.getName());
        }
        if (cityDTO.getInfo() != null) {
            city.setInfo(cityDTO.getInfo());
        }
    }

    private void checkCityForNotNull(City city) throws CityExistenceException {
        if (city == null) {
            throw new CityExistenceException(CITY_NONEXISTENCE_EXCEPTION);
        }
    }

    private City convertDTOToDatabaseObject(CityDTO cityDTO) {
        City city = new City();
        if (cityDTO.getName() != null) {
            city.setName(cityDTO.getName());
        }
        if (cityDTO.getInfo() != null) {
            city.setInfo(cityDTO.getInfo());
        }
        return city;
    }

    private CityDTO convertDatabaseObjectToDTO(City city) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setInfo(city.getInfo());
        return cityDTO;
    }
}
