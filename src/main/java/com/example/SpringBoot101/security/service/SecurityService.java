package com.example.SpringBoot101.security.service;

import com.example.SpringBoot101.security.entity.AppRole;
import com.example.SpringBoot101.security.entity.AppUser;

public interface SecurityService {
    AppUser saveNewUser(String username, String password, String rePassword);
    AppRole saveNewRole(String roleName, String description);
    void addRoleToUser(String username, String roleName);
    void removeRoleFromUser(String username, String roleName);
    AppUser loadUserByUserName(String username);
}
