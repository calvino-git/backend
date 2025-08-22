package com.example.backend.antiheroes.controller;

import com.example.backend.antiheroes.dto.AntiHeroDto;
import com.example.backend.antiheroes.entity.AntiHero;
import com.example.backend.antiheroes.service.AntiHeroService;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(allowedHeaders = "Content-type")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/anti-heroes")
public class AntiHeroController {
    private final AntiHeroService antiHeroService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AntiHeroDto> create(@Valid @RequestBody AntiHeroDto dto) {
        AntiHero created = antiHeroService.createAntiHeroe(modelMapper.map(dto, AntiHero.class));
        AntiHeroDto responseDto = modelMapper.map(created, AntiHeroDto.class);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    // The @Cacheable annotation is used to cache the result of this method.
    // The value attribute specifies the name of the cache, and the key attribute specifies the cache
    // key to use for this specific method call. In this case, it caches the result
    // of the getById method using the UUID id as the key.
    // When this method is called with a specific id, the result will be cached,
    // and subsequent calls with the same id will return the cached result instead of executing the method
    // again, improving performance by avoiding unnecessary database queries.
    public ResponseEntity<AntiHeroDto> getById(@PathVariable UUID id) {
        AntiHero antiHero = antiHeroService.getAntiHeroeById(id);
        AntiHeroDto dto = modelMapper.map(antiHero, AntiHeroDto.class);
        return ResponseEntity.ok(dto);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<AntiHeroDto>> getAll() {
        List<AntiHeroDto> dtos = ((List<AntiHero>) antiHeroService.getAllAntiHeroes())
            .stream()
            .map(entity -> modelMapper.map(entity, AntiHeroDto.class))
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AntiHeroDto> update(@PathVariable UUID id, @Valid @RequestBody AntiHeroDto dto) {
        AntiHero updated = antiHeroService.updateAntiHeroe(id, modelMapper.map(dto, AntiHero.class));
        AntiHeroDto responseDto = modelMapper.map(updated, AntiHeroDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        antiHeroService.deleteAntiHeroe(id);
        return ResponseEntity.noContent().build();
    }
}
