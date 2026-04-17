package com.zeucode.appointment_api.location.repository;

import com.zeucode.appointment_api.location.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    // Para Locations, los métodos básicos (findById, findAll, save) que vienen por defecto suelen ser suficientes.
}
