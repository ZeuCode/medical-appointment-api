package com.zeucode.appointment_api.appointment.service;

import com.zeucode.appointment_api.appointment.entity.Appointment;

public interface AppointmentService {

    // El caso de uso principal del sistema
    Appointment scheduleAppointment(Long patientId, Long timeSlotId);

}
