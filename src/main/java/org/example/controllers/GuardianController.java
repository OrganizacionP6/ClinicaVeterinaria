package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dtos.GuardianRequest;
import org.example.dtos.GuardianResponse;
import org.example.services.GuardianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardians")
public class GuardianController {
    private final GuardianService guardianService;

    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @GetMapping
    public List<GuardianResponse> getGuardians(@RequestParam(name = "name", required = false) String name) {

        if (name != null && !name.isEmpty()) {
            return guardianService.searchByName(name);
        }
        List<GuardianResponse> guardianList = guardianService.getAllGuardians();
        return guardianList;
    }


    @PostMapping
    public ResponseEntity<GuardianResponse> createGuardian(@Valid @RequestBody GuardianRequest guardianRequest){

        GuardianResponse guardian = guardianService.createGuardian(guardianRequest);
        return new ResponseEntity<>(guardian, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public GuardianResponse getGuardianById (@PathVariable int id){
        return guardianService.findGuardianById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteGuardian(@PathVariable int id) {
            guardianService.deleteById(id);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<GuardianResponse> updateGuardian(@PathVariable int id, @RequestBody GuardianRequest guardianRequest){
        GuardianResponse guardian = guardianService.updateGuardian(id, guardianRequest);
        return new ResponseEntity<>(guardian, HttpStatus.OK);
    }



}

