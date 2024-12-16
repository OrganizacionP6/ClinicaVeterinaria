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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
@Profile({"!test", "prod"})
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
            Faker faker = new Faker();
            
            // Crear y guardar Guardianes
            List<Guardian> guardianList = IntStream.range(0, 2)
                  .mapToObj(i -> new Guardian(
                        faker.name().firstName(),
                        faker.phoneNumber().cellPhone(),
                        faker.internet().emailAddress(),
                        faker.address().fullAddress()))
                  .toList();
            List<Guardian> savedGuardians = guardianRepository.saveAll(guardianList);
            
            // Crear y guardar Mascotas
            List<Pet> petList = IntStream.range(0, 2)
                  .mapToObj(i -> new Pet(
                        faker.animal().name(),
                        faker.options().option("gato", "perro", "conejo"),
                        "",
                        faker.number().numberBetween(1, 10),
                        savedGuardians.get(i)))
                  .toList();
            List<Pet> savedPets = petRepository.saveAll(petList);
            
            // Crear y guardar Citas
            List<Appointment> appointmentList = IntStream.range(0, 2)
                  .mapToObj(i -> {
                      LocalDate futureDate = LocalDate.now().plusDays(faker.number().numberBetween(1, 365));
                      LocalTime appointmentTime = LocalTime.parse(faker.options().option("10:25", "11:00", "14:30"));
                      
                      Appointment appointment = new Appointment();
                      appointment.setDate(futureDate);
                      appointment.setTime(appointmentTime);
                      appointment.setReason(faker.medical().diseaseName());
                      appointment.setPet(savedPets.get(i));
                      return appointment;
                  })
                  .toList();
            
            appointmentRepository.saveAll(appointmentList);
            
        };
        
    }
}