CREATE TABLE if NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    customer_id varchar(250) not null,
    first_name varchar(250) not null,
    last_name varchar(250) not null,
    email varchar(250),
    location varchar(250),
    unique(customer_id)
);

CREATE INDEX IF NOT EXISTS customer_customer_id_idx ON customer USING btree(customer_id);