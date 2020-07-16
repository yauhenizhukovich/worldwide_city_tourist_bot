package com.gmail.yauhenizhukovich.reslivtestapp.controller;

import com.gmail.yauhenizhukovich.reslivtestapp.service.CityService;
import com.gmail.yauhenizhukovich.reslivtestapp.service.exception.CityExistenceException;
import com.gmail.yauhenizhukovich.reslivtestapp.service.model.CityDTO;
import com.gmail.yauhenizhukovich.reslivtestapp.service.model.UpdateCityDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Object addCity(
            @RequestBody @Valid CityDTO city,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            List<ObjectError> bindingResultErrors = bindingResult.getAllErrors();
            bindingResultErrors.forEach(error -> errors.add(error.getDefaultMessage()));
            return errors;
        }
        try {
            cityService.addCity(city);
        } catch (CityExistenceException e) {
            return e.getMessage();
        }
        return "City was successfully added.";
    }

    @DeleteMapping("/{name}")
    public String deleteCity(
            @PathVariable String name
    ) {
        try {
            cityService.deleteCityByName(name);
        } catch (CityExistenceException e) {
            return e.getMessage();
        }
        return "City was successfully removed.";
    }

    @PutMapping("/{name}")
    public Object updateCity(
            @RequestBody @Valid UpdateCityDTO city,
            @PathVariable String name,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            List<ObjectError> bindingResultErrors = bindingResult.getAllErrors();
            bindingResultErrors.forEach(error -> errors.add(error.getDefaultMessage()));
            return errors;
        }
        try {
            cityService.updateCityByName(city, name);
        } catch (CityExistenceException e) {
            return e.getMessage();
        }
        return "City was successfully updated.";
    }
}
