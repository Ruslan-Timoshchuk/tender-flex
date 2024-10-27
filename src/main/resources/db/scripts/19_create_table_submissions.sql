CREATE TABLE submissions (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bidder_id INTEGER NOT NULL REFERENCES users(id),
    tender_id INTEGER NOT NULL REFERENCES tenders(id),
    tender_status VARCHAR NOT NULL,
    offer_status VARCHAR NOT NULL,
    submitted_date TIMESTAMP,
    last_updated TIMESTAMP,
    reject_decision_id INTEGER REFERENCES rejects(id)
);