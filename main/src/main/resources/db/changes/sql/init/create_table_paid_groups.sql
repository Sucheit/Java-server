create table if not exists paid_groups
(
    id   integer primary key,
    cost integer check (10 < cost and cost < 1000) not null,
    constraint fk foreign key (id) references groups (id) on delete cascade on update restrict
);
