CREATE TABLE procurement_protocols (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tender_id INTEGER NOT NULL REFERENCES tenders(id),
    offer_id INTEGER NOT NULL REFERENCES offers(id),
    offer_status_contractor VARCHAR NOT NULL,
    award_decision_id INTEGER REFERENCES awards(id),
    created_date TIMESTAMP,
    last_updated TIMESTAMP
);