package com.example.backend.AntiHeroes.service;

import com.example.backend.AntiHeroes.entity.AntiHeroe;
import com.example.backend.AntiHeroes.repository.AntiHeroeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.example.backend.exception.NotFoundException;

@Service
public class AntiHeroeService {
    private final AntiHeroeRepository antiHeroeRepository;

    public AntiHeroeService(AntiHeroeRepository antiHeroeRepository) {
        this.antiHeroeRepository = antiHeroeRepository;
    }

    public AntiHeroe createAntiHeroe(AntiHeroe antiHeroe) {
        return antiHeroeRepository.save(antiHeroe);
    }

    public AntiHeroe getAntiHeroeById(UUID id) {
        return findOrThrow(id);
    }

    public Iterable<AntiHeroe> getAllAntiHeroes() {
        return antiHeroeRepository.findAll();
    }

    public AntiHeroe updateAntiHeroe(UUID id, AntiHeroe antiHeroeData) {
        AntiHeroe antiHeroe = findOrThrow(id);
        antiHeroe.setFirstName(antiHeroeData.getFirstName());
        antiHeroe.setLastName(antiHeroeData.getLastName());
        antiHeroe.setHouse(antiHeroeData.getHouse());
        antiHeroe.setKnowAs(antiHeroeData.getKnowAs());
        return antiHeroeRepository.save(antiHeroe);
    }

    public void deleteAntiHeroe(UUID id) {
        antiHeroeRepository.deleteById(id);
    }

    public AntiHeroe findByFirstName(String firstName) {
        return antiHeroeRepository.findByFirstName(firstName);
    }

    public AntiHeroe findByLastName(String lastName) {
        return antiHeroeRepository.findByLastName(lastName);
    }

    public AntiHeroe findByHouse(String house) {
        return antiHeroeRepository.findByHouse(house);
    }

    public AntiHeroe findByKnowAs(String knowAs) {
        return antiHeroeRepository.findByKnowAs(knowAs);
    }

    public AntiHeroe findOrThrow(UUID id) {
        return antiHeroeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AntiHeroe with id " + id + " not found"));
    }


}
