package com.zeucode.appointment_api.patient.repository;

import com.zeucode.appointment_api.patient.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Spring crea el SQL: SELECT * FROM patients WHERE document_number = ?
    Optional<Patient> findByDocumentNumber(String documentNumber);

    // Súper útil para validaciones al crear un paciente
    boolean existsByEmail(String email);
    boolean existsByDocumentNumber(String documentNumber);
}
