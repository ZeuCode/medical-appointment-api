-- 1. Master Tables
CREATE TABLE specialties
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(100)             NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE
);

CREATE TABLE locations
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(100)             NOT NULL,
    address    VARCHAR(255)             NOT NULL,
    timezone   VARCHAR(50)              NOT NULL DEFAULT 'America/Lima',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE
);

-- 2. System Actors
CREATE TABLE patients
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    document_number VARCHAR(20)              NOT NULL UNIQUE,
    first_name      VARCHAR(100)             NOT NULL,
    last_name       VARCHAR(100)             NOT NULL,
    email           VARCHAR(150)             NOT NULL UNIQUE,
    phone           VARCHAR(20),
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE
);

CREATE TABLE doctors
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    specialty_id    BIGINT                   NOT NULL,
    medical_license VARCHAR(50)              NOT NULL UNIQUE,
    first_name      VARCHAR(100)             NOT NULL,
    last_name       VARCHAR(100)             NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_doctor_specialty FOREIGN KEY (specialty_id) REFERENCES specialties (id)
);

-- 3. Inventory & Availability Core
CREATE TABLE doctor_schedule_templates
(
    id                    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    doctor_id             BIGINT                   NOT NULL,
    day_of_week           INTEGER                  NOT NULL,
    start_time            TIME WITHOUT TIME ZONE NOT NULL,
    end_time              TIME WITHOUT TIME ZONE NOT NULL,
    slot_duration_minutes INTEGER                  NOT NULL,
    created_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_template_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id),
    CONSTRAINT chk_day_of_week CHECK (day_of_week BETWEEN 1 AND 7)
);

CREATE TABLE time_slots
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    doctor_id       BIGINT                   NOT NULL,
    location_id     BIGINT                   NOT NULL,
    start_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    end_timestamp   TIMESTAMP WITH TIME ZONE NOT NULL,
    status          VARCHAR(20)              NOT NULL DEFAULT 'AVAILABLE',
    version         INTEGER                  NOT NULL DEFAULT 0,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_slot_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id),
    CONSTRAINT fk_slot_location FOREIGN KEY (location_id) REFERENCES locations (id),
    CONSTRAINT chk_time_slot_status CHECK (status IN ('AVAILABLE', 'BOOKED', 'BLOCKED'))
);

-- 4. Transactional (Appointments)
CREATE TABLE appointments
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patient_id   BIGINT                   NOT NULL,
    time_slot_id BIGINT                   NOT NULL UNIQUE,
    status       VARCHAR(20)              NOT NULL DEFAULT 'PENDING',
    booking_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients (id),
    CONSTRAINT fk_appointment_slot FOREIGN KEY (time_slot_id) REFERENCES time_slots (id),
    CONSTRAINT chk_appointment_status CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED'))
);

-- 5. Performance Indexes
CREATE INDEX idx_time_slots_search ON time_slots (doctor_id, start_timestamp, status);
CREATE INDEX idx_appointments_patient ON appointments (patient_id);
