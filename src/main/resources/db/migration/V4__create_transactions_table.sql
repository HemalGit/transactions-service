CREATE TABLE if NOT EXISTS transactions (
    id uuid PRIMARY KEY,
    transaction_time timestamp with time zone not null,
    customer_id varchar(250) not null,
    quantity smallint not null,
    product_code varchar(250) not null,
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
    CONSTRAINT fk_product_code FOREIGN KEY (product_code) REFERENCES product(product_code)
);

CREATE INDEX IF NOT EXISTS transactions_customer_id_idx ON transactions USING btree(customer_id);
CREATE INDEX IF NOT EXISTS transactions_product_code_idx ON transactions USING btree(product_code);