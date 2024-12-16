package org.example.dtos;


import jakarta.validation.constraints.*;
import org.example.entities.Appointment;

import java.util.List;

public record PetRequest(
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The name must contain only letters.")
        @NotBlank(message = "The name cannot be blank")
        @NotEmpty(message = "The name cannot be empty.")
        @NotNull(message = "The name cannot be null.")
        @Size(max = 15, message = "The name cannot be greater than 15 numbers.")
        String name,
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The name must contain only letters.")
        @NotBlank(message = "The name cannot be blank")
        @NotEmpty(message = "The name cannot be empty.")
        @NotNull(message = "The name cannot be null.")
        @Size (max = 15, message = "The name cannot be greater than 15 numbers.")
        String specie,
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The name must contain only letters.")
        @NotBlank(message = "The name cannot be blank")
        @NotEmpty(message = "The name cannot be empty.")
        @NotNull(message = "The name cannot be null.")
        @Size (max = 50, message = "The name cannot be greater than 50 numbers.")
        String breed,
        @NotNull(message = "The name cannot be null.")
        @Min(0)
        @Max(50)
        int age,
        int guardianId

) {
}
