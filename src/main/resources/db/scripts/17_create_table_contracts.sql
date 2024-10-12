CREATE TABLE contracts (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    file_id INTEGER REFERENCES files(id),
    award_id INTEGER REFERENCES awards(id),
    signed_deadline TIMESTAMP NOT NULL
);