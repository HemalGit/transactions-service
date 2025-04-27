CREATE TABLE if NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    product_code varchar(250) not null,
    cost numeric(19,2) not null,
    status varchar(250) not null,
    unique(product_code)
);

CREATE INDEX IF NOT EXISTS product_product_code_idx ON product USING btree(product_code);