package com.example.backend.antiheroes.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AntiHeroDto {
    private UUID id;
    @NotNull(message = "Firstname is required.")
    private String firstName;
    private String lastName;
    private String house;
    private String knowAs;
}