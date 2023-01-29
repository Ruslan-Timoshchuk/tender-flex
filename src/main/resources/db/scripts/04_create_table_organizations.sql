CREATE TABLE organizations (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    organization_name VARCHAR(50) NOT NULL,
    national_registration_number VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    city VARCHAR(50) NOT NULL,
    contact_person_id INTEGER REFERENCES contact_persons(id)
);