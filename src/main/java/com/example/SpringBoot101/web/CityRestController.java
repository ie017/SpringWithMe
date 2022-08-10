package com.example.SpringBoot101.web;

import com.example.SpringBoot101.DAO.CityRepository;
import com.example.SpringBoot101.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Deuxieme methode pour obtenir les Api rest.
@RestController
public class CityRestController {
    @Autowired
    private CityRepository cityRepository;
    @GetMapping(path = "/AllCities")
    public List<City> allCities(){
        return cityRepository.findAll();
    }

}
