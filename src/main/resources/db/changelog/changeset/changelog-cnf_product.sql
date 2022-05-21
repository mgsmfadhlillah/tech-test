--liquibase formatted sql

--changeset roft:1
CREATE TABLE cnf_product
(
    product_id   INT AUTO_INCREMENT NOT NULL,
    category_id  INT                NULL,
    product_name VARCHAR(80)        NOT NULL,
    quantity     INT                NOT NULL,
    price        INT                NOT NULL,
    CONSTRAINT pk_cnf_product PRIMARY KEY (product_id)
);

--changeset roft:2
ALTER TABLE cnf_product
    ADD CONSTRAINT cnf_product_uk UNIQUE (product_name);

--changeset roft:3
ALTER TABLE cnf_product
    ADD CONSTRAINT FK_CNF_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES cnf_category (category_id);