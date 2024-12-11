CREATE TABLE offers (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bidder_id INTEGER NOT NULL REFERENCES users(id),
    tender_id INTEGER NOT NULL REFERENCES tenders(id),
    company_profile_id INTEGER NOT NULL REFERENCES company_profiles(id), 
    global_status VARCHAR NOT NULL,
    bid_price BIGINT NOT NULL,
    currency_id INTEGER NOT NULL REFERENCES currencies(id),
    publication_date TIMESTAMP NOT NULL,
    proposition_file_id INTEGER NOT NULL REFERENCES files(id),
    reject_decision_id INTEGER REFERENCES rejects(id)
);