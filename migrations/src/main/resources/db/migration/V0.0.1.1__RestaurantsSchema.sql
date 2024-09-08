CREATE TABLE restaurants.seats
(
    id           UUID         NOT NULL,
    version      INTEGER      NOT NULL,
    name         VARCHAR(255) NOT NULL,
    floor_number SMALLINT,
    type         VARCHAR(64),
    capacity     SMALLINT
);
ALTER TABLE restaurants.seats
    ADD CONSTRAINT seats_pk PRIMARY KEY (id),
    ADD CONSTRAINT seats_name_unique UNIQUE (name);

CREATE TABLE restaurants.reservations
(
    id                       UUID        NOT NULL,
    version                  INTEGER     NOT NULL,
    seat_id                  UUID        NOT NULL,
    user_id                  UUID        NOT NULL,
    reservation_date         DATE        NOT NULL,
    time_slot_from_inclusive TIME        NOT NULL,
    time_slot_to_exclusive   TIME        NOT NULL,
    status                   VARCHAR(64) NOT NULL,
    number_of_guests         SMALLINT,
    created_date             TIMESTAMP   NOT NULL,
    created_by               VARCHAR(64) NOT NULL,
    last_modified_date       TIMESTAMP   NOT NULL,
    last_modified_by         VARCHAR(64) NOT NULL
);
ALTER TABLE restaurants.reservations
    ADD CONSTRAINT reservations_pk PRIMARY KEY (id),
    ADD CONSTRAINT reservations_seat_id_fk FOREIGN KEY (seat_id) REFERENCES restaurants.seats (id),
    ADD CONSTRAINT reservations_user_id_fk FOREIGN KEY (user_id) REFERENCES users.users (id);
CREATE INDEX reservations_seat_id_idx ON restaurants.reservations (seat_id);
CREATE INDEX reservations_user_id_idx ON restaurants.reservations (user_id);

CREATE TABLE restaurants.menu_items
(
    id          UUID         NOT NULL,
    version     INTEGER      NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price       DECIMAL(10, 2),
    category    VARCHAR(64)
);
ALTER TABLE restaurants.menu_items
    ADD CONSTRAINT menu_items_pk PRIMARY KEY (id),
    ADD CONSTRAINT menu_items_name_unique UNIQUE (name);


CREATE TABLE restaurants.reservation_menu_items
(
    reservation_id UUID NOT NULL,
    menu_item_id   UUID NOT NULL
);
ALTER TABLE restaurants.reservation_menu_items
    ADD CONSTRAINT reservation_menu_items_pk PRIMARY KEY (reservation_id, menu_item_id),
    ADD CONSTRAINT reservation_menu_items_reservation_id_fk FOREIGN KEY (reservation_id) REFERENCES restaurants.reservations (id),
    ADD CONSTRAINT reservation_menu_items_menu_item_id_fk FOREIGN KEY (menu_item_id) REFERENCES menu_items (id);
CREATE INDEX reservation_menu_items_reservation_id_idx ON restaurants.reservation_menu_items (reservation_id);
CREATE INDEX reservation_menu_items_menu_item_id_idx ON restaurants.reservation_menu_items (menu_item_id);