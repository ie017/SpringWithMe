package com.example.SpringBoot101;

import com.example.SpringBoot101.DAO.CityRepository;
import com.example.SpringBoot101.entity.City;
import com.example.SpringBoot101.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringBoot101Application implements CommandLineRunner {
    @Autowired
	private CityRepository cityRepository;
	@Autowired
	private SecurityService securityService;
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot101Application.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		cityRepository.save(new City(12, "Rabat", "Rabat-sale-kenitre", "maroc"));
		cityRepository.save(new City(13, "Casablanca", "casablanca-settat", "maroc"));
		cityRepository.save(new City(10, "Agadir", "Souss-massa", "maroc"));

		cityRepository.findAll().forEach(p -> System.out.println(p.getName()));
		/*
		// Ce code doit execute une seule fois
		securityService.saveNewUser("ie023","1234", "1234");
		securityService.saveNewUser("ie024","1234", "1234");
		securityService.saveNewUser("ie025","1234", "1234");

		securityService.saveNewRole("USER", "Just react");
		securityService.saveNewRole("ADMIN","Can do more");

		securityService.addRoleToUser("ie023", "ADMIN");
		securityService.addRoleToUser("ie023", "USER");
		securityService.addRoleToUser("ie024", "USER");*/
	}

}
