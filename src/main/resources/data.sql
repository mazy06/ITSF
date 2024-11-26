CREATE SCHEMA IF NOT EXISTS ITSF;

CREATE TABLE ITSF.Customer (
   customer_id BIGINT PRIMARY KEY,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   age INTEGER
);

INSERT INTO ITSF.Customer (customer_id, first_name, last_name, age) VALUES (1, 'Toufik', 'Mazy', 34);
INSERT INTO ITSF.Customer (customer_id, first_name, last_name, age) VALUES (2, 'Gérard', 'Mazy', 25);
INSERT INTO ITSF.Customer (customer_id, first_name, last_name, age) VALUES (3, 'Test', 'Test', 35);