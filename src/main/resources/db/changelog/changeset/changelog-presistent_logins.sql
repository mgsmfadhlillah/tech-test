--liquibase formatted sql

--changeset roft:1
create table if not exists persistent_logins (
    username varchar(255) not null,
    series varchar(64) primary key,
    token varchar(64) not null,
    last_used timestamp not null
);