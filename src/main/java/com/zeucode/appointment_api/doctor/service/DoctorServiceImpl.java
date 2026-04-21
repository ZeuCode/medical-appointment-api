package com.zeucode.appointment_api.doctor.service;

import com.zeucode.appointment_api.common.exception.ConflictException;
import com.zeucode.appointment_api.common.exception.ResourceNotFoundException;
import com.zeucode.appointment_api.doctor.entity.Doctor;
import com.zeucode.appointment_api.doctor.repository.DoctorRepository;
import com.zeucode.appointment_api.specialty.entity.Specialty;
import com.zeucode.appointment_api.specialty.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository; // Necesitamos validar que la especialidad exista!

    @Override
    @Transactional
    public Doctor createDoctor(Doctor doctor) {
        // 1. Validar Especialidad
        Specialty specialty = specialtyRepository.findById(doctor.getSpecialty().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Specialty not found"));

        doctor.setSpecialty(specialty);

        // 2. Validar Licencia Médica Única
        if (doctorRepository.existsByMedicalLicense(doctor.getMedicalLicense())) {
            throw new ConflictException("Medical license is already registered");
        }

        return doctorRepository.save(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
