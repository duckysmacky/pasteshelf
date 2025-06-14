CREATE TABLE pastes (
    id UUID PRIMARY KEY,
    hash VARCHAR(32) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    content TEXT NOT NULL
);