package com.zeucode.appointment_api.patient.service;

import com.zeucode.appointment_api.patient.entity.Patient;
import java.util.List;

public interface PatientService {
    Patient createPatient(Patient patient);
    Patient getPatientById(Long id);
    List<Patient> getAllPatients();
}
