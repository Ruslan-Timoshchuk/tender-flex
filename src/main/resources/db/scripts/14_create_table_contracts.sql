CREATE TABLE contracts (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    status VARCHAR,
    offer_submission_deadline TIMESTAMP NOT NULL,
    signed_contract_deadline TIMESTAMP NOT NULL
);