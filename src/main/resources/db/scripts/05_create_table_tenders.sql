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
    publication TIMESTAMP NOT NULL,
    deadline TIMESTAMP NOT NULL,
    deadlineForSignedContract TIMESTAMP,
    contractFileName VARCHAR NOT NULL,
    awardDecisionFileName VARCHAR NOT NULL,
    rejectDecisionFileName VARCHAR NOT NULL
);