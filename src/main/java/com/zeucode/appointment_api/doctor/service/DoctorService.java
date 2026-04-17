package com.zeucode.appointment_api.doctor.service;

import com.zeucode.appointment_api.doctor.model.entity.Doctor;
import java.util.List;

public interface DoctorService {
    Doctor createDoctor(Doctor doctor);
    Doctor getDoctorById(Long id);
    List<Doctor> getAllDoctors();
}
