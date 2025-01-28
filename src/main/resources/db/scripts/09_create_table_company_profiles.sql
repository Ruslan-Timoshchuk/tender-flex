CREATE TABLE company_profiles (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    official_name VARCHAR NOT NULL,
    registration_number VARCHAR NOT NULL,
    country_id INTEGER NOT NULL REFERENCES countries(id),
    city VARCHAR NOT NULL, 
    contact_first_name VARCHAR NOT NULL,
    contact_last_name VARCHAR NOT NULL,
    contact_phone_number VARCHAR(15) NOT NULL
);