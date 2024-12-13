package org.example.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;


public record AppointmentRequest(

      //  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format YYYY-MM-DD-.")
       // @FutureOrPresent(message = "The appointment date must be today or in the future.")
      //  @NotBlank(message = "The date cannot be blank")
       // @NotEmpty(message = "The date cannot be empty.")
        @NotNull(message = "The date cannot be null.")
        LocalDate date,
        //@Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "The time must be in HH:mm format.")
       // @NotEmpty(message = "The time cannot be empty.")
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
