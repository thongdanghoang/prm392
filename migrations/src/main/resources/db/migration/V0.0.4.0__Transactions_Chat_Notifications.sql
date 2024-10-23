CREATE TABLE notifications.notifications
(
    id      UUID,
    version INTEGER NOT NULL,
    user_id UUID    NOT NULL
);
ALTER TABLE notifications.notifications
    ADD CONSTRAINT notifications_pk PRIMARY KEY (id),
    ADD CONSTRAINT notifications_user_id_fk FOREIGN KEY (user_id) REFERENCES users.users (id);
CREATE INDEX notifications_user_id_idx ON notifications.notifications (user_id);

CREATE TABLE notifications.user_notification_preferences
(
    id                UUID,
    version           INTEGER     NOT NULL,
    notification_type VARCHAR(64) NOT NULL,
    enabled           BOOLEAN     NOT NULL,
    frequency         VARCHAR(64) NOT NULL,
    user_id           UUID        NOT NULL
);
ALTER TABLE notifications.user_notification_preferences
    ADD CONSTRAINT user_notification_preferences_pk PRIMARY KEY (id),
    ADD CONSTRAINT user_notification_preferences_user_id_fk FOREIGN KEY (user_id) REFERENCES users.users (id);
CREATE INDEX user_notification_preferences_user_id_idx ON notifications.user_notification_preferences (user_id);

ALTER TABLE users.users
    ADD CONSTRAINT users_phone_unique UNIQUE (phone);


CREATE TABLE transactions.transactions
(
    id                 UUID,
    version            INTEGER     NOT NULL,
    created_date       TIMESTAMP   NOT NULL,
    created_by         VARCHAR(64) NOT NULL,
    last_modified_date TIMESTAMP   NOT NULL,
    last_modified_by   VARCHAR(64) NOT NULL,
    user_id            UUID        NOT NULL,
    amount             DECIMAL(10, 2),
    status             VARCHAR(64) NOT NULL,
    transaction_type   VARCHAR(64) NOT NULL,
    payment_method     VARCHAR(64) NOT NULL
);
ALTER TABLE transactions.transactions
    ADD CONSTRAINT transactions_pk PRIMARY KEY (id),
    ADD CONSTRAINT transactions_user_id_fk FOREIGN KEY (user_id) REFERENCES users.users (id);
CREATE INDEX transactions_user_id_idx ON transactions.transactions (user_id);
