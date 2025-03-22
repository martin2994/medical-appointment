-- V1.0.0__init.sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE patient (
                         uuid UUID PRIMARY KEY ,
                         name VARCHAR(255) NOT NULL,
                         surname VARCHAR(255) NOT NULL,
                         birthdate DATE NOT NULL,
                         social_security_number VARCHAR(15) NOT NULL UNIQUE
);