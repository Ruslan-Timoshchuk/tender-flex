CREATE TABLE tenders (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    contractor_id INTEGER NOT NULL REFERENCES users(id), 
    company_profile_id INTEGER NOT NULL REFERENCES company_profiles(id),   
    procedure_type VARCHAR NOT NULL,
    language VARCHAR NOT NULL,
    cpv_id INTEGER NOT NULL REFERENCES cpvs(id),
    description VARCHAR(250) NOT NULL,
    global_status VARCHAR NOT NULL,
    publication_date TIMESTAMP NOT NULL,
    offer_submission_deadline TIMESTAMP NOT NULL
);