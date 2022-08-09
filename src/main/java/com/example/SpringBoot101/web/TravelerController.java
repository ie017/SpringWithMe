package com.example.SpringBoot101.web;

import com.example.SpringBoot101.DAO.TravelerRepository;
import com.example.SpringBoot101.entity.Traveler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.TransferQueue;

@Controller
public class TravelerController {
    @Autowired
    private TravelerRepository travelerRepository;
    @GetMapping(path = "/addTraveler")
    public String addTraveler(Model model){
        model.addAttribute("traveler",  new Traveler());
        return "addTraveler";
    }
    @PostMapping(path = "/saveTraveler")
    public String saveTraveler(@Validated Traveler T, BindingResult bindingResult){
        if (bindingResult.hasErrors()){return "addTraveler";} //reviens vers la page addTraveler si il exist des erreurs.
        travelerRepository.save(T);
        return "addTraveler";
    }
}
