CREATE TABLE visit
(
    uuid           UUID PRIMARY KEY,
    patient_uuid   UUID         NOT NULL,
    date           DATE         NOT NULL,
    time           TIME         NOT NULL,
    type           VARCHAR(255) NOT NULL,
    reason         VARCHAR(255) NOT NULL,
    family_history VARCHAR(255) NOT NULL,
    CONSTRAINT fk_patient FOREIGN KEY (patient_uuid) REFERENCES patient (uuid) ON DELETE CASCADE
);