--liquibase formatted sql

--changeset live4code:create-table-states
create table if not exists telegram.states (
    chatId bigint                    not null,
    state  telegram.state_enum_value not null,
    constraint chat_id_pkey primary key (chatId)
);