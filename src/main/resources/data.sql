insert into groups (name, description)
values ('group1Name', 'group1Desc');
insert into groups (name, description)
values ('group2NamePaid', 'group2DescPaid');
insert into paid_groups(id, cost)
values (2, 99);


insert into users (first_name, last_name)
values ('Steve', 'Rogers');
insert into users (first_name, last_name)
values ('Natasha', 'Romanoff');


insert into user_groups (user_id, group_id)
values (1, 1);
insert into user_groups (user_id, group_id)
values (1, 2);
insert into user_groups (user_id, group_id)
values (2, 2);