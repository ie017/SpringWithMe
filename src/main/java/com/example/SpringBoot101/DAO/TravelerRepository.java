package com.example.SpringBoot101.DAO;

import com.example.SpringBoot101.entity.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelerRepository extends JpaRepository<Traveler, Long> {

}
