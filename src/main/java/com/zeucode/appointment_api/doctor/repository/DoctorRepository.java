package com.zeucode.appointment_api.doctor.repository;

import com.zeucode.appointment_api.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByMedicalLicense(String medicalLicense);
    boolean existsByMedicalLicense(String medicalLicense);
}
