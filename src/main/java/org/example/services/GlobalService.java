package org.example.services;
/*
import org.example.dtos.GlobalRequest;
import org.springframework.stereotype.Service;

@Service
public class GlobalService {

    private final AppointmentService appointmentService;
    private final GuardianService guardianService;
    private final PetService petService;

    public GlobalService(AppointmentService appointmentService, GuardianService guardianService, PetService petService) {
        this.appointmentService = appointmentService;
        this.guardianService = guardianService;
        this.petService = petService;
    }

    public GlobalService getGlobalService() {
        long totalAppointments = appointmentService.countAppointments();
        long totalGuardians = guardianService.countGuardians();
        long totalPets = petService.countPets();

        return new GlobalService(totalAppointments, totalGuardians, totalPets);
    }
}*/