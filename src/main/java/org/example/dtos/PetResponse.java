package org.example.dtos;


public record PetResponse(
        int id,
        String name,
        String specie,
        String breed,
        int age,
        GuardianResponse guardian
) {
}
