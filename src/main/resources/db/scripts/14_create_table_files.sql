CREATE TABLE files (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR,
    content_type VARCHAR,
    aws_s3_file_key VARCHAR
);