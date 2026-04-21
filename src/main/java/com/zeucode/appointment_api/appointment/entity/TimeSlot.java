package com.zeucode.appointment_api.appointment.entity;

import com.zeucode.appointment_api.appointment.enums.SlotStatus;
import com.zeucode.appointment_api.common.entity.AuditableEntity;
import com.zeucode.appointment_api.doctor.entity.Doctor;
import com.zeucode.appointment_api.location.entity.Location;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "time_slots")
public class TimeSlot extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "start_timestamp", nullable = false)
    private Instant startTimestamp;

    @Column(name = "end_timestamp", nullable = false)
    private Instant endTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SlotStatus status = SlotStatus.AVAILABLE;

    @Version // MAGIA PROFESIONAL: Activa el Optimistic Locking automáticamente
    private Integer version;
}
