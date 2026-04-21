package com.zeucode.appointment_api.patient.controller;

import com.zeucode.appointment_api.patient.dto.PatientRequestDTO;
import com.zeucode.appointment_api.patient.dto.PatientResponseDTO;
import com.zeucode.appointment_api.patient.entity.Patient;
import com.zeucode.appointment_api.patient.mapper.PatientMapper;
import com.zeucode.appointment_api.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper; // ¡Inyectamos nuestro Mapper!

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO request) {

        // 1. Mapeo a Entidad (1 línea)
        Patient patientToSave = patientMapper.toEntity(request);

        // 2. Guardado en BD (1 línea)
        Patient savedPatient = patientService.createPatient(patientToSave);

        // 3. Mapeo a DTO y retorno (1 línea)
        return new ResponseEntity<>(patientMapper.toResponseDTO(savedPatient), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patientMapper.toResponseDTO(patient));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patients = patientService.getAllPatients().stream()
            .map(patientMapper::toResponseDTO) // Uso de Method Reference (Súper limpio)
            .collect(Collectors.toList());

        return ResponseEntity.ok(patients);
    }
}
