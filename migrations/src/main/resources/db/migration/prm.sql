CREATE TABLE "reservations"
(
	"id" UUID NOT NULL UNIQUE,
	"version" INTEGER NOT NULL,
	"transaction_id" UUID NOT NULL,
	"seat_id" UUID NOT NULL,
	"user_id" UUID NOT NULL,
	"reservation_date" DATE NOT NULL,
	"time_slot_from_inclusive" TIME NOT NULL,
	"time_slot_to_exclusive" TIME NOT NULL,
	-- (e.g., confirmed, cancelled)
	"status" VARCHAR(50) NOT NULL,
	"number_of_guests" SMALLSERIAL,
	"created_date" TIMESTAMP NOT NULL,
	"created_by" VARCHAR(255) NOT NULL,
	"last_modified_date" TIMESTAMP NOT NULL,
	"last_modified_by" VARCHAR(255) NOT NULL,
	PRIMARY KEY("id")
);
COMMENT ON COLUMN reservations.status IS '(e.g., confirmed, cancelled)';


CREATE TABLE "seats"
(
	"id" UUID NOT NULL UNIQUE,
	"name" VARCHAR(255) NOT NULL,
	-- (e.g., window, booth, outdoor)
	"type" VARCHAR(255),
	"capacity" SMALLSERIAL,
	"floor_number" SMALLSERIAL NOT NULL,
	PRIMARY KEY("id")
);
COMMENT ON COLUMN seats.type IS '(e.g., window, booth, outdoor)';


CREATE TABLE "reservation_menu_items"
(
	"reservation_id" UUID NOT NULL UNIQUE,
	"menu_item_id" UUID NOT NULL,
	"quantity" SMALLSERIAL NOT NULL,
	PRIMARY KEY("reservation_id", "menu_item_id")
);


CREATE TABLE "menu_items"
(
	"id" UUID NOT NULL UNIQUE,
	"name" VARCHAR(255) NOT NULL UNIQUE,
	"description" VARCHAR(255),
	"price" BIGINT NOT NULL,
	--  (e.g., starter, main_course, dessert)
	"category" VARCHAR(50),
	PRIMARY KEY("id")
);
COMMENT ON COLUMN menu_items.category IS ' (e.g., starter, main_course, dessert)';


ALTER TABLE "reservations"
ADD FOREIGN KEY("seat_id") REFERENCES "seats"("id")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "reservation_menu_items"
ADD FOREIGN KEY("reservation_id") REFERENCES "reservations"("id")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "reservation_menu_items"
ADD FOREIGN KEY("menu_item_id") REFERENCES "menu_items"("id")
ON UPDATE NO ACTION ON DELETE NO ACTION;