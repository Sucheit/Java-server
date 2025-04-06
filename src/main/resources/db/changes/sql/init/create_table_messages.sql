create table messages (
    id UUID  primary key,
    message_id varchar(36) not null,
    message varchar(36) not null
);