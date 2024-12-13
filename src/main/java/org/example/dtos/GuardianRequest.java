package org.example.dtos;


import jakarta.validation.constraints.*;

public record GuardianRequest(
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The name must contain only letters.")
        @NotBlank(message = "The name cannot be blank")
        @NotEmpty(message = "The name cannot be empty.")
        @NotNull(message = "The name cannot be null.")
        @Size (max = 25, message = "The name cannot be greater than 25 numbers.")
        String name,
        @NotBlank(message = "The email cannot be blank")
        @NotEmpty(message = "The email cannot be empty.")
        @NotNull(message = "The email cannot be null.")
        @Email
        String email,
        @Pattern(regexp = "\\d+", message = "The phone must contain only numbers.")
        @NotBlank(message = "The phone cannot be blank")
        @NotEmpty(message = "The phone cannot be empty.")
        @NotNull
        @Size(min = 8, max = 9, message = "The phone cannot be less than 8 numbers and greater than 9.")
        String phone,
        @NotBlank(message = "The address cannot be blank")
        @Size (min = 8, max = 20, message = "The address cannot be less than 8 numbers and greater than 20.")
        @NotEmpty(message = "The address cannot be empty.")
        @NotNull
        String address
){
}
