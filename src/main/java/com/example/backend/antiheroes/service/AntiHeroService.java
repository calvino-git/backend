package com.example.backend.antiheroes.service;

import com.example.backend.antiheroes.entity.AntiHero;
import com.example.backend.antiheroes.repository.AntiHeroRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.example.backend.exception.NotFoundException;

@Service
public class AntiHeroService {
    private final AntiHeroRepository antiHeroRepository;

    public AntiHeroService(AntiHeroRepository antiHeroRepository) {
        this.antiHeroRepository = antiHeroRepository;
    }

    public AntiHero createAntiHeroe(AntiHero antiHero) {
        return antiHeroRepository.save(antiHero);
    }

    @Cacheable(value = "antiHeroes", key = "#id")
    public AntiHero getAntiHeroeById(UUID id) {
        return findOrThrow(id);
    }

    @Cacheable(value = "antiHeroes")
    public Iterable<AntiHero> getAllAntiHeroes() {
        return antiHeroRepository.findAll();
    }

    @CachePut(value = "antiHeroes", key = "#id")
    public AntiHero updateAntiHeroe(UUID id, AntiHero antiHeroData) {
        AntiHero antiHero = findOrThrow(id);
        antiHero.setFirstName(antiHeroData.getFirstName());
        antiHero.setLastName(antiHeroData.getLastName());
        antiHero.setHouse(antiHeroData.getHouse());
        antiHero.setKnowAs(antiHeroData.getKnowAs());
        return antiHeroRepository.save(antiHero);
    }

    @CacheEvict(value = "antiHeroes", key = "#id")
    public void deleteAntiHeroe(UUID id) {
        antiHeroRepository.deleteById(id);
    }

    public AntiHero findByFirstName(String firstName) {
        return antiHeroRepository.findByFirstName(firstName);
    }

    public AntiHero findByLastName(String lastName) {
        return antiHeroRepository.findByLastName(lastName);
    }

    public AntiHero findByHouse(String house) {
        return antiHeroRepository.findByHouse(house);
    }

    public AntiHero findByKnowAs(String knowAs) {
        return antiHeroRepository.findByKnowAs(knowAs);
    }

    public AntiHero findOrThrow(UUID id) {
        return antiHeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AntiHeroe with id " + id + " not found"));
    }


}
