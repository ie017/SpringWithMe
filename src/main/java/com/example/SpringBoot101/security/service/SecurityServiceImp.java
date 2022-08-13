package com.example.SpringBoot101.security.service;

import com.example.SpringBoot101.security.entity.AppRole;
import com.example.SpringBoot101.security.entity.AppUser;
import com.example.SpringBoot101.security.repository.AppRoleRepository;
import com.example.SpringBoot101.security.repository.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImp implements SecurityService{
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        if (!password.equals(rePassword)) throw new RuntimeException("Password not match");
        String hashedPWD = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashedPWD);
        appUser.setActive(true);
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole findAppRole = appRoleRepository.findByRoleName(roleName);
        if (findAppRole != null) throw new RuntimeException("Role "+roleName+" already exist");
        AppRole appRole = new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedAppRole = appRoleRepository.save(appRole);
        return savedAppRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
         AppUser findAppUser = appUserRepository.findByUsername(username);
         if (findAppUser == null) throw new RuntimeException("User not found");
         AppRole findAppRole = appRoleRepository.findByRoleName(roleName);
         if (findAppRole == null) throw new RuntimeException("Role not found");
         findAppUser.getAppRoles().add(findAppRole);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser findAppUser = appUserRepository.findByUsername(username);
        if (findAppUser == null) throw new RuntimeException("User not found");
        AppRole findAppRole = appRoleRepository.findByRoleName(roleName);
        if (findAppRole == null) throw new RuntimeException("Role not found");
        findAppUser.getAppRoles().remove(findAppRole);
    }

    @Override
    public AppUser loadUserByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }
}
