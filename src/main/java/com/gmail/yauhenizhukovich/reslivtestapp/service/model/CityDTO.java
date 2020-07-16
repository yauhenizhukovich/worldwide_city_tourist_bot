package com.gmail.yauhenizhukovich.reslivtestapp.service.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CityDTO {
    @NotEmpty(message = "City name cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "City name can contain only letters.")
    private String name;
    @NotEmpty(message = "City info cannot be empty.")
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
