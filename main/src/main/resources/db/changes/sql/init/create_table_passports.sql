create table if not exists passports
(
    user_id       integer    not null,
    serial_number varchar(5) not null,
    constraint fk_user_id foreign key (user_id) references users (id) on delete cascade on update restrict
);
