package com.example.SpringBoot101.security.repository;

import com.example.SpringBoot101.security.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
