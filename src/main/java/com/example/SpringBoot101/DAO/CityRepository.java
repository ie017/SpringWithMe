package com.example.SpringBoot101.DAO;

import com.example.SpringBoot101.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    public Page<City> findByNameContains(String mc, Pageable pageable);
    /* JpaRepository est une extension spécifique JPA (Java Persistence API) de Repository.
    Il contient l'API complète de CrudRepository et PagingAndSortingRepository.
    Il contient donc une API pour les opérations CRUD de base et également une API pour la pagination et le tri.*/
}
