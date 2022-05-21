--liquibase formatted sql

--changeset roft:1
create table app_role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(50) not null,
    constraint role_uk
        unique (role_name)
);

--changeset roft:2
create table app_user
(
    user_id            int auto_increment
        primary key,
    fullname           varchar(255) not null,
    password           varchar(250) not null,
    phone_number       varchar(16)  null,
    address            varchar(250) not null,
    created_by         varchar(50)  null,
    join_date          datetime     null,
    email              varchar(255) not null,
    enabled            tinyint(1)   not null,
    account_non_locked tinyint(1)   null,
    failed_attempt     int          null,
    lock_time          datetime     null,
    role_id            int          null,
    constraint user_email_uk
        unique (email),
    constraint user_phone_uk
        unique (phone_number),
    constraint FK_appuser_approle_roleid
        foreign key (role_id) references db_telkomsel.app_role (role_id)
);

--changeset roft:3
INSERT INTO db_telkomsel.app_role (role_id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO db_telkomsel.app_role (role_id, role_name) VALUES (2, 'ROLE_BUYER');

--changeset roft:4
INSERT INTO db_telkomsel.app_user (user_id, fullname, password, phone_number, address, created_by, join_date, email, enabled, account_non_locked, failed_attempt, lock_time, role_id) VALUES (1, 'Admin', '$2a$10$EFEcGf1k3bBObyh7wUtfGucCUq64/Q0J.1N2ZpBn1JJZFlEh9sMHa', '08119933771', 'Jl. P.A.K.Abdurohim No.306', null, NOW(), 'admin@gmail.com', 1, 0, 0, null, 1);
INSERT INTO db_telkomsel.app_user (user_id, fullname, password, phone_number, address, created_by, join_date, email, enabled, account_non_locked, failed_attempt, lock_time, role_id) VALUES (2, 'Buyer', '$2a$10$EFEcGf1k3bBObyh7wUtfGucCUq64/Q0J.1N2ZpBn1JJZFlEh9sMHa', '08119911881', 'Jl. P.A.K.Abdurohim No.306', null, NOW(), 'buyer@gmail.com', 1, 0, 0, null, 2);

--changeset roft:5
alter table app_user modify enabled boolean not null;
alter table app_user modify account_non_locked boolean not null;

