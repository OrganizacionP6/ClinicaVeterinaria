package org.example.controllers;

import org.example.dtos.PetRequest;
import org.example.dtos.PetResponse;
import org.example.entities.Pet;
import org.example.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetResponse> createPet(@RequestBody PetRequest petRequest) {
        PetResponse pet = petService.createPet(petRequest);
        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PetResponse> showAllPets(){
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public PetResponse getPetById(@PathVariable int id){
        return petService.findByIdPets(id);
    }


    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable int id){
        this.petService.deletePetById(id);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable int id, @RequestBody PetRequest petRequest){
        PetResponse pet = petService.updatePetById(id, petRequest);

        return new ResponseEntity<>(pet, HttpStatus.OK);
    }
}
