CREATE TABLE offers (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bidder_id INTEGER NOT NULL REFERENCES users(id),
    submission_id INTEGER NOT NULL REFERENCES submissions(id),
    official_name VARCHAR NOT NULL,
    registration_number VARCHAR NOT NULL,
    country_id INTEGER NOT NULL REFERENCES countries(id),
    city VARCHAR NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    bid_price BIGINT NOT NULL,
    currency_id INTEGER NOT NULL REFERENCES currencies(id),
    publication_date TIMESTAMP NOT NULL,
    proposition_file_id INTEGER NOT NULL REFERENCES files(id)
);