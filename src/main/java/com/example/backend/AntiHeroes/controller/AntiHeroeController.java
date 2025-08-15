package com.example.backend.AntiHeroes.controller;

import com.example.backend.AntiHeroes.dto.AntiHeroeDto;
import com.example.backend.AntiHeroes.entity.AntiHeroe;
import com.example.backend.AntiHeroes.service.AntiHeroeService;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1/anti-heroes")
public class AntiHeroeController {
    private final AntiHeroeService antiHeroeService;
    private final ModelMapper modelMapper;

    public AntiHeroeController(AntiHeroeService antiHeroeService, ModelMapper modelMapper) {
        this.antiHeroeService = antiHeroeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<AntiHeroeDto> create(@Valid @RequestBody AntiHeroeDto dto) {
        AntiHeroe created = antiHeroeService.createAntiHeroe(modelMapper.map(dto, AntiHeroe.class));
        AntiHeroeDto responseDto = modelMapper.map(created, AntiHeroeDto.class);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AntiHeroeDto> getById(@PathVariable UUID id) {
        AntiHeroe antiHeroe = antiHeroeService.getAntiHeroeById(id);
        AntiHeroeDto dto = modelMapper.map(antiHeroe, AntiHeroeDto.class);
        return ResponseEntity.ok(dto);
    }

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
