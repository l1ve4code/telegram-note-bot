--liquibase formatted sql

--changeset live4code:create-table-shares
create table if not exists telegram.shares (
    shareUserName varchar not null,
    readUserName  varchar not null,
    constraint share_read_user_pkey primary key (shareUserName, readUserName)
);