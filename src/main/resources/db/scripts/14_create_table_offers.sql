CREATE TABLE offers (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bidder_id INTEGER REFERENCES users(id),
    tender_id INTEGER REFERENCES tenders(id),
    organization_id INTEGER REFERENCES organizations(id),
    bid_price BIGINT NOT NULL,
    currency_id INTEGER REFERENCES currencies(id),
    contractor_status VARCHAR NOT NULL,
    bidder_status VARCHAR NOT NULL,
    publication_date TIMESTAMP NOT NULL,
    document_name VARCHAR NOT NULL
);