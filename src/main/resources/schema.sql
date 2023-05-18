/*
drop table users;
drop table crypto_currencies;
*/

create table if not exists users(id BIGSERIAL primary key, username varchar(40) unique, cryptocurrency_id bigint not null, price_at_register numeric, register_date TIMESTAMP);

create table if not exists cryptocurrencies(id bigint not null primary key, symbol varchar(10) not null, name varchar(30), price_usd numeric);
