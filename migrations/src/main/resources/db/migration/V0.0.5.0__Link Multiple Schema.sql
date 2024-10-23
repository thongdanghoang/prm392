ALTER TABLE restaurants.reservations
    ADD COLUMN transaction_id UUID;
ALTER TABLE  restaurants.reservations
    ADD CONSTRAINT reservations_transaction_id_fk FOREIGN KEY (transaction_id) REFERENCES transactions.transactions (id);
CREATE INDEX reservations_transaction_id_idx ON restaurants.reservations (transaction_id);