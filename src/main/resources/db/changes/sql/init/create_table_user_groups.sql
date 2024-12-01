create table if not exists user_groups
(
    user_id  integer not null,
    group_id integer not null,
    constraint pk primary key (user_id, group_id),
    constraint fk_group_id foreign key (group_id) references groups (id) on delete cascade on update restrict,
    constraint fk_user_id foreign key (user_id) references users (id) on delete cascade on update restrict
);
