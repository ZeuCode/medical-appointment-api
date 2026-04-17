package com.zeucode.appointment_api.appointment.repository;

import com.zeucode.appointment_api.appointment.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Para ver el historial de citas de un paciente ordenado de la más reciente a la más antigua
    List<Appointment> findByPatientIdOrderByBookingTimeDesc(Long patientId);
}
