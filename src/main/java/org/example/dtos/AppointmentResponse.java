package org.example.dtos;


import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponse (
        long id,
        LocalDate date,
        LocalTime time,
        String reason,
        PetResponse pet) {
}
