package com.example.backend.villain;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface VillainRepository extends CrudRepository<Villain, UUID> {
    
    // Custom query to find a villain by their first name
    Villain findByFirstName(String firstName);
    
    // Custom query to find a villain by their last name
    Villain findByLastName(String lastName);
    
    // Custom query to find a villain by their house
    Villain findByHouse(String house);
    
    // Custom query to find a villain by their known alias
    Villain findByKnowAs(String knowAs);
    
}
