CREATE TABLE user_roles (
    user_id INTEGER NOT NULL REFERENCES users(id) ON UPDATE CASCADE,
    role_id INTEGER NOT NULL REFERENCES roles(id) ON UPDATE CASCADE,
    UNIQUE(user_id, role_id)
);