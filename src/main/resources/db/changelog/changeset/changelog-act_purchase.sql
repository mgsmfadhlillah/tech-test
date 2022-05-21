--liquibase formatted sql

--changeset roft:1
CREATE TABLE act_purchase
(
    purchase_id   INT AUTO_INCREMENT NOT NULL,
    user_id       INT                NULL,
    product_id    INT                NULL,
    bank_id       INT                NULL,
    quantity      INT                NOT NULL,
    price         INT                NOT NULL,
    purchase_date datetime           NULL,
    transfer_date datetime           NULL,
    expired_date  datetime           NULL,
    CONSTRAINT pk_act_purchase PRIMARY KEY (purchase_id)
);
--changeset roft:2
ALTER TABLE act_purchase
    ADD CONSTRAINT FK_ACT_PURCHASE_ON_BANK FOREIGN KEY (bank_id) REFERENCES cnf_bank (bank_id);
ALTER TABLE act_purchase
    ADD CONSTRAINT FK_ACT_PURCHASE_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (user_id);
ALTER TABLE act_purchase
    ADD CONSTRAINT FK_ACT_PURCHASE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES cnf_product (product_id);

--changeset roft:3
alter table act_purchase
    add status VARCHAR(1) null after price;
alter table act_purchase
    add payment_code int null after price;