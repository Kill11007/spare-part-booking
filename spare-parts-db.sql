create database if not exists spare_parts_db;
CREATE USER 'spare_parts_db'@'%' IDENTIFIED BY 'spare_parts_db_pwd';
CREATE USER 'spare_parts_db'@'localhost' IDENTIFIED BY 'spare_parts_db_pwd';
select * from mysql.USER;
GRANT ALL privileges on spare_parts_db.* TO 'spare_parts_db'@'%';
GRANT ALL privileges on spare_parts_db.* TO 'spare_parts_db'@'localhost';

use spare_parts_db;

create table if not exists items(
	id bigint primary key auto_increment,
    name varchar(255) not null,
    quantity int,
    price decimal
);

create table if not exists users(
	id bigint primary key auto_increment,
    name varchar(255) not null,
    role enum('USER', 'ADMIN'),
    password varchar(255)
);

create table if not exists orders(
	id bigint primary key auto_increment,
    user_id bigint not null,
    total decimal,
    foreign key (user_id) references users(id)
);

create table if not exists order_items(
	id bigint primary key auto_increment,
    order_id bigint not null,
    item_id bigint not null,
    quantity int not null,
    amount decimal,
    foreign key(order_id) references orders(id),
    foreign key (item_id) references items(id)
);

select * from users;
select * from items;
select * from orders;
select * from order_items;