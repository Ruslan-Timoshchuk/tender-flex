CREATE TABLE contracts (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tender_id INTEGER UNIQUE REFERENCES tenders(id) ON DELETE CASCADE,
    offer_id INTEGER REFERENCES offers(id),
    contract_type_id INTEGER NOT NULL REFERENCES contract_types(id),
    min_price INTEGER NOT NULL,
    max_price INTEGER NOT NULL,
    currency_id INTEGER NOT NULL REFERENCES currencies(id),
    file_id INTEGER NOT NULL REFERENCES files(id),
    signed_deadline TIMESTAMP NOT NULL,
    global_status VARCHAR NOT NULL,
    signed_date TIMESTAMP
);