CREATE TABLE brand (
    id                  BIGSERIAL NOT NULL,
    name                VARCHAR(32) ,
    nationality         VARCHAR(64),
    description         VARCHAR(128)
);
ALTER TABLE brand ADD CONSTRAINT brand_pk PRIMARY KEY (id);

CREATE TABLE model(
    id                 BIGSERIAL NOT NULL,
    name               VARCHAR (32),
    type               VARCHAR (32),
    description        VARCHAR (128),
    brand_id           BIGSERIAL
);
ALTER TABLE model ADD CONSTRAINT model_pk PRIMARY KEY (id);

CREATE TABLE config(
    id                 BIGSERIAL NOT NULL,
    name               VARCHAR (32),
    key_feature        VARCHAR (128),
    year               DATE,
    model_id           BIGSERIAL
);
ALTER TABLE config ADD CONSTRAINT config_pk PRIMARY KEY (id);

CREATE TABLE orders(
    id                 BIGSERIAL NOT NULL,
    order_number       VARCHAR (64),
    price              NUMERIC (10,2),
    purchase_date      DATE,
    requirement        VARCHAR (256),
    config_id          BIGSERIAL
);
ALTER TABLE orders ADD CONSTRAINT orders_pk PRIMARY KEY (id);

CREATE TABLE customer(
    id                 BIGSERIAL NOT NULL,
    name               VARCHAR (32),
    cell_number        VARCHAR (256),
    email              VARCHAR (64),
    information        VARCHAR (256),
    order_id           BIGSERIAL
);
ALTER TABLE customer ADD CONSTRAINT customer_pk PRIMARY KEY (id);

ALTER TABLE customer ADD CONSTRAINT customer_orders_fk FOREIGN KEY(order_id) REFERENCES orders(id);
ALTER TABLE orders ADD CONSTRAINT orders_config_fk FOREIGN KEY(config_id) REFERENCES config(id);
ALTER TABLE config ADD CONSTRAINT config_model_fk FOREIGN KEY(model_id) REFERENCES model(id);
ALTER TABLE model ADD CONSTRAINT model_brand_fk FOREIGN KEY(brand_id) REFERENCES brand(id);





