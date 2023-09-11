--liquibase formatted sql

--changeset live4code:create-enum-state_enum_value
create type telegram.state_enum_value as enum ('NOTE_WAITING');