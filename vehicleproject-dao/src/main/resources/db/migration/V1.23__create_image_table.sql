CREATE TABLE image (
    id              BIGSERIAL NOT NULL,
    object_key      VARCHAR (255),
    user_id         BIGSERIAL
);
ALTER TABLE image ADD CONSTRAINT image_pk PRIMARY KEY(id);
ALTER TABLE image ADD CONSTRAINT image_user_fk FOREIGN KEY(user_id) REFERENCES users(id);