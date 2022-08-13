package com.example.SpringBoot101.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
    @Id // pour definir userId comme un id de tableau AppUser
    private String userId;
    @Column(unique = true) //C'est a dire repetetion des username dans ce colonne la est impossible
    private String username;
    private String password;
    private boolean active;
    // ManyToMany signifie n <---> n
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> appRoles = new ArrayList<>();
    // EAGER la chargement des approles
    // fait automatiqeument une fois de la charge d'un user.

    // FetchType.LAZY est la valeur par defaut c'est a chaque fois que on
    // va charger un user dans la base de données hibernate ne
    // va pas charger les roles en memoire il faut utiliser getappRoles.
}
