CREATE TABLE tenders (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    contractor_id INTEGER REFERENCES users(id),
    organization_id INTEGER REFERENCES organizations(id),
    cpv_code VARCHAR NOT NULL,
    tender_type VARCHAR NOT NULL,
    details VARCHAR(250) NOT NULL,
    min_price BIGINT NOT NULL,
    max_price BIGINT NOT NULL,
    currency VARCHAR NOT NULL,
    publication_date TIMESTAMP NOT NULL,
    deadline TIMESTAMP NOT NULL,
    deadline_for_signed_contract TIMESTAMP,
    status VARCHAR NOT NULL,
    contract_file_name VARCHAR NOT NULL,
    award_decision_file_name VARCHAR NOT NULL,
    reject_decision_file_name VARCHAR NOT NULL
);