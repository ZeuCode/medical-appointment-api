package com.zeucode.appointment_api.doctor.model.entity;

import com.zeucode.appointment_api.common.entity.AuditableEntity;
import com.zeucode.appointment_api.specialty.model.entity.Specialty;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;

    @Column(name = "medical_license", nullable = false, unique = true, length = 50)
    private String medicalLicense;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
}
