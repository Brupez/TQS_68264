package com.buser.repository;

import com.buser.model.City;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    
    public List<City> findByNameContainingIgnoreCase(String name); // Spring Data JPA automaticamente cria uma implementação baseada no nome do método
}
