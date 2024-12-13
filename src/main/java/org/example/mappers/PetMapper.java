package org.example.mappers;

import org.example.dtos.PetRequest;
import org.example.dtos.PetResponse;
import org.example.entities.Guardian;
import org.example.entities.Pet;

public class PetMapper {
    public static Pet fromRequest(PetRequest petRequest, Guardian guardian) {
        return new Pet( petRequest.name(), petRequest.specie(),
                petRequest.breed(), petRequest.age(), guardian

        );
    }
    public static PetResponse toResponse(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecie(),
                pet.getBreed(),
                pet.getAge(),
                GuardianMapper.toResponse(pet.getGuardian())
        );
    }
}
