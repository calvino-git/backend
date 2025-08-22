package com.example.backend.AntiHeroes.controller;

import com.example.backend.AntiHeroes.dto.AntiHeroeDto;
import com.example.backend.AntiHeroes.entity.AntiHeroe;
import com.example.backend.AntiHeroes.service.AntiHeroeService;

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
public class AntiHeroeController {
    private final AntiHeroeService antiHeroeService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AntiHeroeDto> create(@Valid @RequestBody AntiHeroeDto dto) {
        AntiHeroe created = antiHeroeService.createAntiHeroe(modelMapper.map(dto, AntiHeroe.class));
        AntiHeroeDto responseDto = modelMapper.map(created, AntiHeroeDto.class);
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
    public ResponseEntity<AntiHeroeDto> getById(@PathVariable UUID id) {
        AntiHeroe antiHeroe = antiHeroeService.getAntiHeroeById(id);
        AntiHeroeDto dto = modelMapper.map(antiHeroe, AntiHeroeDto.class);
        return ResponseEntity.ok(dto);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<AntiHeroeDto>> getAll() {
        List<AntiHeroeDto> dtos = ((List<AntiHeroe>) antiHeroeService.getAllAntiHeroes())
            .stream()
            .map(entity -> modelMapper.map(entity, AntiHeroeDto.class))
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AntiHeroeDto> update(@PathVariable UUID id, @Valid @RequestBody AntiHeroeDto dto) {
        AntiHeroe updated = antiHeroeService.updateAntiHeroe(id, modelMapper.map(dto, AntiHeroe.class));
        AntiHeroeDto responseDto = modelMapper.map(updated, AntiHeroeDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        antiHeroeService.deleteAntiHeroe(id);
        return ResponseEntity.noContent().build();
    }
}
