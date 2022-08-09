package com.example.SpringBoot101.web;

import com.example.SpringBoot101.DAO.CityRepository;
import com.example.SpringBoot101.entity.City;
import org.hibernate.hql.spi.id.global.GlobalTemporaryTableBulkIdStrategy;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;


import java.security.Key;

@Controller
public class CityController {
    @Autowired
    private CityRepository cityRepository;
    @GetMapping(path = "/city")
    public String getCity(Model model,
                          @RequestParam(name="page", defaultValue = "0") int page,
                          @RequestParam(name="size", defaultValue="3") int size,
                          @RequestParam(name="Keyword",defaultValue = "") String mc){
        Page<City> PageOfcity = cityRepository.findByNameContains(mc, PageRequest.of(page, size)); // Recuperée citys from la couche metie
        model.addAttribute("city", PageOfcity.getContent()); // Stocker les citys dans le model sous le nom Mycity
        model.addAttribute("pages", new int[PageOfcity.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("Keyword", mc);
        model.addAttribute("size", size);
        return("city");
    }
    @GetMapping(path = "/DeleteCity")
    public String deleteCity(Long id, String Keyword, int page, int size, Model model){
        cityRepository.deleteById(id);
        return getCity(model, page, size, Keyword);
    }
    @GetMapping(path = "/DeleteCity2") /* Suppresion using redirect*/
    public String deleteCitySecond(Long id, String Keyword, int page, int size, Model model){
        cityRepository.deleteById(id);
        return "redirect:/city?page="+page+"&size="+size+"&Keyword="+Keyword;
    }
    @GetMapping(path = "/addCity")
    public String addCity(Model model){
        model.addAttribute("city",new City());
        return("addCity");
    }
    @PostMapping(path = "/saveCity")
    public String saveCity(City cityAdded){
        cityRepository.save(cityAdded);
        return "addCity";
    }
}
