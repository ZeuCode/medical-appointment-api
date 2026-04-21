package com.zeucode.appointment_api.appointment.service;

import com.zeucode.appointment_api.appointment.entity.Appointment;
import com.zeucode.appointment_api.appointment.entity.TimeSlot;
import com.zeucode.appointment_api.appointment.enums.AppointmentStatus;
import com.zeucode.appointment_api.appointment.enums.SlotStatus;
import com.zeucode.appointment_api.appointment.repository.AppointmentRepository;
import com.zeucode.appointment_api.appointment.repository.TimeSlotRepository;
import com.zeucode.appointment_api.common.exception.ConflictException;
import com.zeucode.appointment_api.common.exception.ResourceNotFoundException;
import com.zeucode.appointment_api.patient.entity.Patient;
import com.zeucode.appointment_api.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor // Magia de Lombok: Inyecta los repositorios automáticamente sin escribir un Constructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional // LA ANOTACIÓN MÁS IMPORTANTE DEL PROYECTO
    public Appointment scheduleAppointment(Long patientId, Long timeSlotId) {

        // 1. Validar que el paciente existe
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));

        // 2. Validar que el Slot existe
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
            .orElseThrow(() -> new ResourceNotFoundException("Time slot not found with id: " + timeSlotId));

        // 3. REGLA DE NEGOCIO: ¿Está disponible?
        if (timeSlot.getStatus() != SlotStatus.AVAILABLE) {
            throw new ConflictException("The selected time slot is no longer available.");
        }

        // 4. Actualizar el inventario (Bloquear el slot)
        timeSlot.setStatus(SlotStatus.BOOKED);
        timeSlotRepository.save(timeSlot);

        // 5. Crear la cita (La transacción real)
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setTimeSlot(timeSlot);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setBookingTime(Instant.now()); // Aquí aplicamos la lógica que discutimos antes

        // 6. Guardar y devolver
        return appointmentRepository.save(appointment);
    }
}
