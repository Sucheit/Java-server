create table if not exists groups
(
    id          integer primary key generated BY DEFAULT as identity,
    name        varchar(100) not null,
    description varchar(255) not null
);
