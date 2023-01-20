CREATE TABLE organizations (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    nationalRegistrationNumber VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    city VARCHAR(50) NOT NULL,
    contact_person_id INTEGER REFERENCES contact_persons(id)
);