CREATE TABLE offers (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bidder_id INTEGER REFERENCES users(id),
    tender_id INTEGER REFERENCES tenders(id),
    status_id INTEGER REFERENCES offer_statuses(id) DEFAULT 1,
    organization_id INTEGER REFERENCES organizations(id),
    bid_price BIGINT NOT NULL,
    currency_id INTEGER REFERENCES currencies(id),
    publication_date TIMESTAMP NOT NULL,
    document_name VARCHAR NOT NULL,
    award_decision_name VARCHAR,
    reject_decision_name VARCHAR
);