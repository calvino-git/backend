package com.example.backend.AntiHeroes.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.AntiHeroes.entity.AntiHeroe;

@Repository
public interface AntiHeroeRepository extends CrudRepository<AntiHeroe, UUID> {

    // Custom query to find an anti-hero by their first name
    AntiHeroe findByFirstName(String firstName);

    // Custom query to find an anti-hero by their last name
    AntiHeroe findByLastName(String lastName);

    // Custom query to find an anti-hero by their house
    AntiHeroe findByHouse(String house);

    // Custom query to find an anti-hero by their known alias
    AntiHeroe findByKnowAs(String knowAs);

}
