    drop table ss_role if exists;

    drop table ss_user if exists;

    drop table ss_user_role if exists;
    
    drop table ss_log if exists;

    create table ss_role (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table ss_user (
        id bigint generated by default as identity,
        create_time timestamp,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        plain_password varchar(255),
        sha_password varchar(255),
        status varchar(255),
        primary key (id)
    );

    create table ss_user_role (
        user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    );
    
    create table SS_LOG (
    THREAD_NAME varchar(255),
    LOGGER_NAME varchar(255),
    TIMESTAMP timestamp,
    LEVEL varchar(20),
    MESSAGE varchar(255)
    );

    alter table ss_user_role 
        add constraint FK1306854BF125651E 
        foreign key (user_id) 
        references ss_user;

    alter table ss_user_role 
        add constraint FK1306854B4BFAA13E 
        foreign key (role_id) 
        references ss_role;
