package com.zeucode.appointment_api.patient.mapper;

import com.zeucode.appointment_api.patient.dto.PatientRequestDTO;
import com.zeucode.appointment_api.patient.dto.PatientResponseDTO;
import com.zeucode.appointment_api.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

// Le decimos a MapStruct que genere un Bean de Spring (@Component) para poder inyectarlo
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

    Patient toEntity(PatientRequestDTO requestDTO);

    PatientResponseDTO toResponseDTO(Patient patient);

}
