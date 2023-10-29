#### Database Queries


```
insert into roles(id, name) values (1, 'ROLE_ADMIN');
insert into roles(id, name) values (2, 'ROLE_QUOTE_ADMIN');

insert into users_roles (users_id, roles_id) values (5, 1);
insert into users_roles (users_id, roles_id) values (5, 2);

insert into authorities(id, name) values (1, 'READ');
insert into authorities(id, name) values (2, 'WRITE');
insert into authorities(id, name) values (3, 'DELETE');


insert into roles_authorities(roles_id, authorities_id ) values (1, 1);
insert into roles_authorities(roles_id, authorities_id ) values (1, 2);
insert into roles_authorities(roles_id, authorities_id ) values (1, 3);
```
