package com.example.backend.antiheroes.controller;

import com.example.backend.antiheroes.dto.AntiHeroDto;
import com.example.backend.antiheroes.entity.AntiHero;
import com.example.backend.antiheroes.service.AntiHeroService;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@CrossOrigin(allowedHeaders = "Content-type")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/anti-heroes")
public class AntiHeroController {
    private final AntiHeroService antiHeroService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AntiHeroDto> create(@Valid @RequestBody AntiHeroDto dto) {
        AntiHero created = antiHeroService.createAntiHero(modelMapper.map(dto, AntiHero.class));
        AntiHeroDto responseDto = modelMapper.map(created, AntiHeroDto.class);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(responseDto);
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
        AntiHero antiHero = antiHeroService.getAntiHeroById(id);
        AntiHeroDto dto = modelMapper.map(antiHero, AntiHeroDto.class);
        return ResponseEntity.ok(dto);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<AntiHeroDto>> getAll(Pageable pageable) {
        int toSkip = pageable.getPageSize() * pageable.getPageNumber();//
        //SLF4J
        log.info("Using SLF4J: Getting anti hero list - getAntiHeroes()");
        var antiHeroes = StreamSupport
                .stream(antiHeroService.getAllAntiHeroes().spliterator(), false)
                .skip(toSkip)
                .limit(pageable.getPageSize())
                .toList();

        List<AntiHeroDto> dtos = antiHeroes
            .stream()
            .map(entity -> modelMapper.map(entity, AntiHeroDto.class))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AntiHeroDto> update(@PathVariable UUID id, @Valid @RequestBody AntiHeroDto dto) {
        AntiHero updated = antiHeroService.updateAntiHero(id, modelMapper.map(dto, AntiHero.class));
        AntiHeroDto responseDto = modelMapper.map(updated, AntiHeroDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        antiHeroService.deleteAntiHero(id);
        return ResponseEntity.noContent().build();
    }
}
