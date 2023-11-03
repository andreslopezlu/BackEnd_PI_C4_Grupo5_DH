package com.grupo5.AlquilerEquiposConstruccion.service;

import com.grupo5.AlquilerEquiposConstruccion.model.City;
import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> getAllCities();
    Optional<City> getCityById(Integer id);
    City saveCity(City city);
    City updateCity(City city);
    void deleteCityById(Integer id);
}
