CREATE TABLE accounts
(
    id            UUID        NOT NULL,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    email         VARCHAR(50) NOT NULL,
    username      VARCHAR(50) NOT NULL,
    password      VARCHAR(50) NOT NULL,
    profile       VARCHAR(50) NOT NULL,
    version       INTEGER,
    created_date  TIMESTAMPTZ NOT NULL,
    modified_date TIMESTAMP WITH TIME ZONE,

    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX accounts_email_key ON accounts (email);
CREATE UNIQUE INDEX accounts_username_key ON accounts (username);