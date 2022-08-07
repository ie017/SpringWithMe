package com.example.SpringBoot101;

import com.example.SpringBoot101.DAO.CityRepository;
import com.example.SpringBoot101.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot101Application implements CommandLineRunner {
    @Autowired
	private CityRepository cityRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringBoot101Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cityRepository.save(new City(12, "Rabat", "Rabat-sale-kenitre", "maroc"));
		cityRepository.save(new City(13, "Casablanca", "casablanca-settat", "maroc"));
		cityRepository.save(new City(10, "Agadir", "Souss-massa", "maroc"));

		cityRepository.findAll().forEach(p -> System.out.println(p.getName()));
	}

}
