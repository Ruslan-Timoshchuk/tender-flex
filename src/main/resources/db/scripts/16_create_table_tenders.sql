CREATE TABLE tenders (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    contractor_id INTEGER NOT NULL REFERENCES users(id), 
    official_name VARCHAR NOT NULL,
    registration_number VARCHAR NOT NULL,
    country_id INTEGER NOT NULL REFERENCES countries(id),
    city VARCHAR NOT NULL, 
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    procedure_type VARCHAR NOT NULL,
    language VARCHAR NOT NULL,
    cpv_id INTEGER NOT NULL REFERENCES cpvs(id),
    description VARCHAR(250) NOT NULL,
    status VARCHAR NOT NULL,
    publication_date TIMESTAMP NOT NULL,
    offer_submission_deadline TIMESTAMP NOT NULL
);