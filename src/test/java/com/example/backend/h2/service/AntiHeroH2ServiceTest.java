package com.example.backend.h2.service;

import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.backend.antiheroes.entity.AntiHero;
import com.example.backend.antiheroes.repository.AntiHeroRepository;
import com.example.backend.antiheroes.service.AntiHeroService;
import com.example.backend.exception.NotFoundException;


@ActiveProfiles("test")
@DataJpaTest
public class AntiHeroH2ServiceTest {
    @Autowired
    private AntiHeroRepository antiHeroRepository;
    private AntiHeroService antiHeroService;

    @BeforeEach
    public void setUp() {
        //antiHeroRepository.deleteAll();
        antiHeroService = new AntiHeroService(antiHeroRepository);
        //Populate the in-memory database with test data if necessary
        //Create and save antiHero1
        AntiHero antiHero1 = new AntiHero();
        antiHero1.setFirstName("Deadpool");
        antiHero1.setHouse("Regeneration");
        antiHero1.setKnowAs("Marvel");
        antiHero1.setLastName("Wilson");
        antiHeroRepository.save(antiHero1);

        //Create and save antiHero2
        AntiHero antiHero2 = new AntiHero();
        antiHero2.setFirstName("Wolverine");
        antiHero2.setHouse("Regeneration");
        antiHero2.setKnowAs("Marvel");
        antiHero2.setLastName("Logan");
        antiHeroRepository.save(antiHero2);

        //Create and save antiHero3
        AntiHero antiHero3 = new AntiHero();
        antiHero3.setFirstName("Punisher");
        antiHero3.setHouse("Military");
        antiHero3.setKnowAs("Marvel");
        antiHero3.setLastName("Castle");
        antiHeroRepository.save(antiHero3);

        //Create and save antiHero4
        AntiHero antiHero4 = new AntiHero();
        antiHero4.setFirstName("Catwoman");
        antiHero4.setHouse("Stealth");
        antiHero4.setKnowAs("DC");
        antiHero4.setLastName("Kyle");
        antiHeroRepository.save(antiHero4);
    }

    @Test
    void testGetAllAntiHeroes() {
        // Test the service method to retrieve all anti-heroes
        Iterable<AntiHero> antiHeroes = antiHeroService.getAllAntiHeroes();
        // Add assertions to verify the results
        assertThat(antiHeroes).isNotNull();
        assertThat(antiHeroes).isNotEmpty();
        assertThat(antiHeroes).hasSize(4); // Assuming you have 4 anti-heroes in the test data
    }

    @Test
    void shouldCreateNewAntiHero() {
        AntiHero newAntiHero = new AntiHero();
        newAntiHero.setFirstName("Venom");
        newAntiHero.setLastName("Brock");
        newAntiHero.setHouse("Symbiote");
        newAntiHero.setKnowAs("Marvel");

        AntiHero savedAntiHero = antiHeroService.createAntiHero(newAntiHero);

        assertThat(savedAntiHero).isNotNull();
        assertThat(savedAntiHero.getId()).isNotNull();
        assertThat(savedAntiHero).isEqualTo(newAntiHero);
    }

    @Test
    void shouldUpdateAntiHero() {
        Iterable<AntiHero> antiHeroes = antiHeroService.getAllAntiHeroes();
        AntiHero existingAntiHero = antiHeroes.iterator().next();
        existingAntiHero.setLastName("UpdatedLastName");

        AntiHero updatedAntiHero = antiHeroService.updateAntiHero(existingAntiHero.getId(), existingAntiHero);

        assertThat(updatedAntiHero).isNotNull();
        assertThat(updatedAntiHero.getLastName()).isEqualTo("UpdatedLastName");
    }

    @Test
    void shouldDeleteAntiHero() {
        AntiHero antiHero = antiHeroRepository.findByFirstName("Deadpool");
        UUID id = antiHero.getId();
        antiHeroService.deleteAntiHero(antiHero.getId());

        assertThrows(NotFoundException.class, () -> {
            antiHeroService.getAntiHeroById(id);
        });
    }
}
