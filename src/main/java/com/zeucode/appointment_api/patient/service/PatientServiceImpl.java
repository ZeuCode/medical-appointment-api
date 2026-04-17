package com.zeucode.appointment_api.patient.service;

import com.zeucode.appointment_api.common.exception.ConflictException;
import com.zeucode.appointment_api.common.exception.ResourceNotFoundException;
import com.zeucode.appointment_api.patient.model.entity.Patient;
import com.zeucode.appointment_api.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public Patient createPatient(Patient patient) {
        // REGLA DE NEGOCIO: No pueden haber dos pacientes con el mismo email o documento
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new ConflictException("Email is already registered");
        }
        if (patientRepository.existsByDocumentNumber(patient.getDocumentNumber())) {
            throw new ConflictException("Document number is already registered");
        }

        return patientRepository.save(patient);
    }

    @Override
    @Transactional(readOnly = true) // Optimización Senior: Le dice a BD que solo vamos a leer, lo hace más rápido
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}
