package org.example.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequest(
        @NotNull
        LocalDate date,
        LocalTime time,
        String reason,
        int petId) {
}
