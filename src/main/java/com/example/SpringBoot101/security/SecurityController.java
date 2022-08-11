package com.example.SpringBoot101.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {
    @GetMapping(path = "/notAutorized")
    public String notAutorized(){
        return "notAutorized";
    }
    @GetMapping(path = "/login")
    public String login(){
        return "loginPage";
    }
    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request) throws ServletException{
        request.logout(); // Fermer la session
        return "redirect:/login";
    }
}
