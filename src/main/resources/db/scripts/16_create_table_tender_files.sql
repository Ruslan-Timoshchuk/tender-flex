CREATE TABLE tender_files (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tender_id INTEGER REFERENCES tenders(id),
    name VARCHAR,
    file_type VARCHAR,
    content_type VARCHAR,
    aws_s3_file_key VARCHAR
);