--liquibase formatted sql

--changeset live4code:create-schema-telegram
create schema if not exists telegram;