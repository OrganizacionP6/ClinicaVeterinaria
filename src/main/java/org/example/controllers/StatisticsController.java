package org.example.controllers;

import org.example.entities.Statistics;
import org.example.services.GuardianService;
import org.example.services.PetService;
import org.example.services.AppointmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final GuardianService guardianService;
    private final PetService petService;
    private final AppointmentService appointmentService;
    
    public StatisticsController(GuardianService guardianService, PetService petService,
                                AppointmentService appointmentService) {
        this.guardianService = guardianService;
        this.petService = petService;
        this.appointmentService = appointmentService;
    }
    
    @GetMapping("/global")
    public Statistics getGlobalStatistics() {
        long totalGuardians = guardianService.countGuardians();
        long totalPets = petService.countPets();
        long totalAppointments = appointmentService.countAppointments();
        
        return new Statistics(totalAppointments, totalPets, totalGuardians);
    }
}
