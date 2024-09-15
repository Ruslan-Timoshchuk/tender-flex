CREATE TABLE offers (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bidder_id INTEGER REFERENCES users(id),
    official_name VARCHAR NOT NULL,
    registration_number VARCHAR NOT NULL,
    country_id INTEGER REFERENCES countries(id),
    city VARCHAR NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    bid_price BIGINT NOT NULL,
    currency_id INTEGER REFERENCES currencies(id),
    publication_date TIMESTAMP NOT NULL,
    contract_id INTEGER REFERENCES contracts(id),
    proposition_file_id INTEGER REFERENCES offer_files(id),
    decision_file_id INTEGER REFERENCES tender_files(id)
);