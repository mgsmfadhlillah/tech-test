--liquibase formatted sql

--changeset roft:1
CREATE TABLE cnf_bank
(
    bank_id        INT AUTO_INCREMENT NOT NULL,
    bank_name      VARCHAR(50)        NOT NULL,
    account_number VARCHAR(20)        NOT NULL,
    account_name   VARCHAR(200)       NOT NULL,
    balance        INT                NOT NULL,
    CONSTRAINT pk_cnf_bank PRIMARY KEY (bank_id)
);

--changeset roft:2
ALTER TABLE cnf_bank
    ADD CONSTRAINT cnf_bank_uk UNIQUE (bank_name);
