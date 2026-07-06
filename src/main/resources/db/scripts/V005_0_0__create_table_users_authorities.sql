CREATE TABLE users_authorities (
    user_id INTEGER NOT NULL REFERENCES users(id),
    authority_id INTEGER NOT NULL REFERENCES authorities(id),
    UNIQUE(user_id, authority_id)
);