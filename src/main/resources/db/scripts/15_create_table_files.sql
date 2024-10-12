CREATE TABLE files (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR,
    file_type VARCHAR,
    content_type VARCHAR,
    aws_s3_file_key VARCHAR
);