--liquibase formatted sql

--changeset live4code:create-table-users
create table if not exists telegram.users (
    userName varchar not null,
    chatId   bigint  not null,
    constraint user_name_user_id_pkey primary key (userName, chatId)
);