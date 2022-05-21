--liquibase formatted sql

--changeset roft:1
CREATE TABLE cnf_category
(
    category_id      INT AUTO_INCREMENT NOT NULL,
    category_name    VARCHAR(80)        NOT NULL,
    subcategory_name VARCHAR(80)        NOT NULL,
    CONSTRAINT pk_cnf_category PRIMARY KEY (category_id)
);

--changeset roft:2
ALTER TABLE cnf_category
    ADD CONSTRAINT cnf_category_uk UNIQUE (category_name, subcategory_name);