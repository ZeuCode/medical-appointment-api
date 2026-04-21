package com.zeucode.appointment_api.doctor.repository;

import com.zeucode.appointment_api.doctor.entity.DoctorScheduleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorScheduleTemplateRepository extends JpaRepository<DoctorScheduleTemplate, Long> {

    // Trae toda la plantilla de trabajo de un doctor específico
    List<DoctorScheduleTemplate> findByDoctorId(Long doctorId);
}
