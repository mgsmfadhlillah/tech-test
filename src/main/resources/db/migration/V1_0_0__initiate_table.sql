create table app_role
(
    role_id int auto_increment
        primary key,
    role_name varchar(50) not null,
    constraint role_uk
        unique (role_name)
);


create table app_user
(
    user_id      int auto_increment
        primary key,
    address      varchar(250) not null,
    created_by   varchar(50)  null,
    join_date    datetime     null,
    email        varchar(255) not null,
    enabled      int          not null,
    fullname     varchar(255) not null,
    password     varchar(250) not null,
    phone_number varchar(16)  null,
    role_id      int          null,
    constraint user_email_uk
        unique (email),
    constraint user_phone_uk
        unique (phone_number),
    constraint FK_appuser_approle_roleid
        foreign key (role_id) references app_role (role_id)
);

