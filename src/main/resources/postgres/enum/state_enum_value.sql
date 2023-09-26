--liquibase formatted sql

--changeset live4code:create-enum-state_enum_value
create type telegram.state_enum_value as enum ('NOTE_WAITING');

--changeset live4code:add-new-enum-value-to-state_enum_value
alter type telegram.state_enum_value add value 'DELETE_NOTE_WAITING';

--changeset live4code:add-new-enum-value-to-state_enum_value_SHARE_USERNAME_WAITING
alter type telegram.state_enum_value add value 'SHARE_USERNAME_WAITING';

--changeset live4code:add-new-enum-value-to-state_enum_value_STOP_SHARE_USERNAME_WAITING
alter type telegram.state_enum_value add value 'STOP_SHARE_USERNAME_WAITING';
