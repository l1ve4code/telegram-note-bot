--liquibase formatted sql

--changeset live4code:create-table-notifications
create table if not exists telegram.notifications (
    id           serial  primary key,
    chatId       bigint  not null,
    notification varchar,
    date         date,
    time         time,
    isSent       boolean not null default false
);