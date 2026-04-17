package com.zeucode.appointment_api.appointment.repository;

import com.zeucode.appointment_api.appointment.model.entity.TimeSlot;
import com.zeucode.appointment_api.appointment.model.enums.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    // ESTO ES NIVEL SENIOR: Usar @Query para consultas de negocio complejas
    @Query("SELECT ts FROM TimeSlot ts WHERE ts.doctor.id = :doctorId " +
        "AND ts.startTimestamp >= :start " +
        "AND ts.endTimestamp <= :end " +
        "AND ts.status = :status ORDER BY ts.startTimestamp ASC")
    List<TimeSlot> findAvailableSlots(
        @Param("doctorId") Long doctorId,
        @Param("start") Instant start,
        @Param("end") Instant end,
        @Param("status") SlotStatus status
    );
}
