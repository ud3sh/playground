CREATE TABLE hotels (
id serial primary key,
name varchar
);
CREATE TABLE rooms (
        id serial primary key,
        number integer,
        hotel int references hotels(id)
);