insert into groups (name, description)
values ('group1Name', 'group1Desc'),
       ('group2NamePaid', 'group2DescPaid');
insert into paid_groups(id, cost)
values (2, 99);


insert into users (first_name, last_name)
values ('Steve', 'Rogers'),
       ('Natasha', 'Romanoff'),
       ('Tony', 'Stark'),
       ('Clint', 'Barton'),
       ('Nick', 'Fury'),
       ('Phil', 'Coulson'),
       ('Sam', 'Williams'),
       ('Wanda', 'Maximoff'),
       ('Scott', 'Lang'),
       ('Peter', 'Quill'),
       ('Peter', 'Parker'),
       ('Petter', 'Potts'),
       ('Peggy', 'Carter'),
       ('Yelena', 'Belova'),
       ('Marc', 'Spector'),
       ('Steven', 'Grant'),
       ('Kamala', 'Khan'),
       ('Jeniffer', 'Walters'),
       ('Jack', 'Russell'),
       ('Kate', 'Bishop'),
       ('Jane', 'Foster')
;

insert into passports (user_id, serial_number)
values (1, '12345'),
       (2, '09876'),
       (3, '13579');

insert into user_groups (user_id, group_id)
values (1, 1),
       (1, 2),
       (2, 2);