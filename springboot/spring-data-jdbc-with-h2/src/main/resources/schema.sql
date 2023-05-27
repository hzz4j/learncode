create table student
(
    id              integer      not null,
    name            varchar(255) not null,
    passport_number varchar(255) not null,
    primary key (id)
);

create TABLE client
(
    id   INT auto_increment primary key,
    name VARCHAR(200)
);

create TABLE project
(
    id     INT auto_increment primary key,
    name   VARCHAR(200),
    client INTEGER
);
ALTER TABLE project
    ADD FOREIGN KEY (client) REFERENCES client (id);
