package com.example.backend.antiheroes.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.antiheroes.entity.AntiHero;

@Repository
public interface AntiHeroRepository extends CrudRepository<AntiHero, UUID> {

    // Custom query to find an anti-hero by their first name
    AntiHero findByFirstName(String firstName);

    // Custom query to find an anti-hero by their last name
    AntiHero findByLastName(String lastName);

    // Custom query to find an anti-hero by their house
    AntiHero findByHouse(String house);

    // Custom query to find an anti-hero by their known alias
    AntiHero findByKnowAs(String knowAs);

}
