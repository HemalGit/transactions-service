CREATE TABLE if NOT EXISTS auth_user (
    id SERIAL PRIMARY KEY,
    email varchar(250) not null,
    password varchar(250) not null,
    first_name varchar(250) not null,
    last_name varchar(250) not null,
    roles json
);

CREATE INDEX IF NOT EXISTS auth_user_email_idx ON auth_user USING btree(email);