CREATE TABLE users.users
(
    id                 UUID,
    version            INTEGER      NOT NULL,
    username           VARCHAR(64)  NOT NULL,
    password           VARCHAR(255) NOT NULL,
    email              VARCHAR(320),
    phone              VARCHAR(15),
    first_name         VARCHAR(255),
    last_name          VARCHAR(255),
    avatar             VARCHAR(255),
    birthday           DATE,
    created_date       TIMESTAMP    NOT NULL,
    created_by         VARCHAR(64)  NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL,
    last_modified_by   VARCHAR(64)  NOT NULL
);
ALTER TABLE users.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id),
    ADD CONSTRAINT users_username_unique UNIQUE (username),
    ADD CONSTRAINT users_email_unique UNIQUE (email);

CREATE TABLE users.authorities
(
    id      UUID,
    version INTEGER     NOT NULL,
    role    VARCHAR(64) NOT NULL
);
ALTER TABLE users.authorities
    ADD CONSTRAINT authorities_pk PRIMARY KEY (id),
    ADD CONSTRAINT authorities_role_unique UNIQUE (role);

CREATE TABLE users.users_authorities
(
    user_id      UUID NOT NULL,
    authority_id UUID NOT NULL
);
ALTER TABLE users.users_authorities
    ADD CONSTRAINT users_authorities_pk PRIMARY KEY (user_id, authority_id),
    ADD CONSTRAINT users_authorities_user_id_fk FOREIGN KEY (user_id) REFERENCES users.users (id),
    ADD CONSTRAINT users_authorities_authority_id_fk FOREIGN KEY (authority_id) REFERENCES users.authorities (id);
CREATE INDEX users_authorities_user_id_idx ON users.users_authorities (user_id);
CREATE INDEX users_authorities_authority_id_idx ON users.users_authorities (authority_id);
