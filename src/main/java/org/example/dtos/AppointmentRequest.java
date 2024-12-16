package org.example.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;


public record AppointmentRequest(


        @FutureOrPresent(message = "The appointment date must be today or in the future.")
        @NotNull(message = "The date cannot be null.")
        LocalDate date,
        @NotNull(message = "The time cannot be null.")
        LocalTime time,
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The reason must contain only letters.")
        @NotBlank(message = "The reason cannot be blank")
        @NotEmpty(message = "The reason cannot be empty.")
        @NotNull(message = "The reason cannot be null.")
        String reason,
        int petId
) {

}
