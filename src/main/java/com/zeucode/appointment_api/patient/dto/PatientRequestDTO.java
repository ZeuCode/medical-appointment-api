package com.zeucode.appointment_api.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Un Record es un DTO inmutable perfecto. No necesita Lombok, getters ni setters.
public record PatientRequestDTO(

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    String firstName,

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    String lastName,

    @NotBlank(message = "Document number is mandatory")
    @Size(min = 8, max = 20, message = "Document number must be between 8 and 20 characters")
    String documentNumber,

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is not valid")
    String email,

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Phone number must be valid and contain only digits (and optional +)")
    String phone
) {}
