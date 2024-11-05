CREATE TABLE contracts (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tender_id INTEGER NOT NULL REFERENCES tenders(id),
    offer_id INTEGER REFERENCES offers(id),
    contract_type_id INTEGER NOT NULL REFERENCES contract_types(id),
    min_price INTEGER NOT NULL,
    max_price INTEGER NOT NULL,
    currency_id INTEGER NOT NULL REFERENCES currencies(id),
    contract_file_id INTEGER NOT NULL REFERENCES files(id),
    signed_deadline TIMESTAMP NOT NULL,
    signed_date TIMESTAMP
);