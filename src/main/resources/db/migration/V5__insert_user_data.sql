INSERT INTO auth_user (email, password, first_name, last_name, roles)
VALUES ('hemalK@gmail.com','$2a$10$XS8lxsx9hPYDAoyihe8W7Ot8SU.AoJlf0NaC6piHWQuPi4jjldg/6', 'Hemal', 'Kariyawasam', '["TX_CUSTOMER"]'::json),
       ('joeD@gmail.com','$2a$10$WcWHMZlOr.E7zpw8pDTmGu0WiZpXc9HabrTolsczDNojNjw2TGGUC', 'Joe', 'Daniel', '["TX_ADMIN","TX_REPORTING"]'::json);