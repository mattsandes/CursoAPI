ALTER TABLE person
    ADD COLUMN enabled BOOLEAN NOT NULL DEFAULT TRUE;

UPDATE person
SET enabled = TRUE;

