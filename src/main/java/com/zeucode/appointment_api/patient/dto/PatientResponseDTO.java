package com.zeucode.appointment_api.patient.dto;

public record PatientResponseDTO(
    Long id,
    String firstName,
    String lastName,
    String documentNumber,
    String email,
    String phone
) {}
