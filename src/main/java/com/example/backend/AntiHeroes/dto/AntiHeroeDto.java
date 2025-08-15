package com.example.backend.AntiHeroes.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AntiHeroeDto {
    private UUID id;
    @NotNull(message = "Firstname is required.")
    private String firstName;
    private String lastName;
    private String house;
    private String knowAs;
}