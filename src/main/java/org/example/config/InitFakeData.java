package org.example.config;

import com.github.javafaker.Faker;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("!test")
public class InitFakeData {
    
    @Autowired
    private GuardianRepository guardianRepository;
    
    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Bean
    @Transactional
    public CommandLineRunner initialData() {
        return args -> {
            // Limpiar las tablas antes de insertar nuevos datos
            appointmentRepository.deleteAll();
            petRepository.deleteAll();
            guardianRepository.deleteAll();
            
            // Instancia de Faker para generar datos ficticios
            Faker faker = new Faker();
            
            // Lista para almacenar los guardianes generados
            List<Guardian> guardianList = new ArrayList<>();
            
            // Generar y guardar 3 guardianes con datos ficticios
            for (int i = 0; i < 3; i++) {
                Guardian guardian = new Guardian();
                guardian.setName(faker.name().fullName());
                guardian.setEmail(faker.internet().emailAddress());
                guardian.setPhone(faker.phoneNumber().cellPhone());
                guardian.setAddress(faker.address().fullAddress());
                
                // Agregar el guardián a la lista
                guardianList.add(guardian);
            }
            
            // Guardar todos los guardianes en el repositorio
            List<Guardian> savedGuardians = guardianRepository.saveAll(guardianList);
            
            // Lista para almacenar las mascotas generadas
            List<Pet> petList = new ArrayList<>();
            
            // Generar y guardar 2 mascotas por cada guardián
            for (Guardian guardian : savedGuardians) {
                for (int j = 0; j < 1; j++) {
                    Pet pet = new Pet();
                    pet.setName(faker.animal().name());
                    pet.setSpecie(faker.options().option("Gato", "Perro", "Conejo"));
                    pet.setAge(faker.number().numberBetween(1, 10));
                    pet.setGuardian(guardian);
                    
                    // Agregar la mascota a la lista
                    petList.add(pet);
                }
            }
            
            // Guardar todas las mascotas en el repositorio
            List<Pet> savedPets = petRepository.saveAll(petList);
            
            // Lista para almacenar las citas generadas
            List<Appointment> appointmentList = new ArrayList<>();
            
            // Generar y guardar 1 cita por cada mascota
            for (Pet pet : savedPets) {
                Appointment appointment = new Appointment();
                appointment.setDate(LocalDate.now().plusDays(faker.number().numberBetween(1, 365)));
                appointment.setTime(LocalTime.of(faker.number().numberBetween(8, 17), faker.options().option(0, 15, 30, 45)));
                appointment.setReason(faker.medical().diseaseName());
                appointment.setPet(pet);
                
                // Agregar la cita a la lista
                appointmentList.add(appointment);
            }
            
            // Guardar todas las citas en el repositorio
            appointmentRepository.saveAll(appointmentList);
        };
    }
}
