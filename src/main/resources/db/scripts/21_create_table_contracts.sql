CREATE TABLE contracts (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tender_id INTEGER NOT NULL REFERENCES tenders(id),
    offer_id INTEGER REFERENCES offers(id),
    contract_file_id INTEGER NOT NULL REFERENCES files(id),
    award_id INTEGER NOT NULL REFERENCES awards(id),
    signed_deadline TIMESTAMP NOT NULL,
    signed_date TIMESTAMP NOT NULL
);