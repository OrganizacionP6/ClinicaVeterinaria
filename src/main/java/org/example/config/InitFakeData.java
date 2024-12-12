package org.example.config;

import org.example.entities.Appointment;
import org.example.entities.Guardian;
import org.example.entities.Pet;
import org.example.repositories.AppointmentRepository;
import org.example.repositories.GuardianRepository;
import org.example.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

@Configuration
@Profile("!test")
public class InitFakeData {

    @Autowired
    GuardianRepository guardianRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            List<Guardian> guardianList = List.of(
                    new Guardian("Paloma", "655014845", "palomita@gmail.com", "mi direccion1"),
                    new Guardian("nombre2", "677876545", "ggg@gmail.com", "mi direccion2"));
            List<Guardian> savedGuardians = guardianRepository.saveAll(guardianList);

            Guardian guardian1 = savedGuardians.get(0);
            Guardian guardian2 = savedGuardians.get(1);

            List<Pet> petList = List.of(
                    new Pet("Bobby", "gato", "", 5, guardian1),
                    new Pet("Mike", "gato", "", 3, guardian2));
            List<Pet> savedPets = petRepository.saveAll(petList);

            Pet pet1 = savedPets.get(0);
            Pet pet2 = savedPets.get(1);

            List<Appointment> appointmentList = List.of(
                    new Appointment("11/12/2024", "10:25", "Vaccination", pet1),
                    new Appointment("12/12/2024", "11:00", "Follow-Up", pet2));
            appointmentRepository.saveAll(appointmentList);
        };
    }
}