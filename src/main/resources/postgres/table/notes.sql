--liquibase formatted sql

--changeset live4code:create-table-notes
create table if not exists telegram.notes (
    id     serial  primary key,
    chatId bigint  not null,
    note   varchar not null
);