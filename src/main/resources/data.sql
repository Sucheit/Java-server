insert into groups (group_type, name, description)
values ('group', 'group1', 'group1desc1');
insert into groups (group_type, name, description, cost)
values ('paid_group', 'group2', 'group2desc2', 99);


insert into users (first_name, last_name)
values ('emma', 'hill');
insert into users (first_name, last_name)
values ('tom', 'anderson');


insert into user_groups (user_id, group_id)
values (1, 1);
insert into user_groups (user_id, group_id)
values (1, 2);
insert into user_groups (user_id, group_id)
values (2, 2);