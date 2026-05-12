-- script.sql
create table member(
    username varchar2(50) PRIMARY KEY ,
    password varchar2(100) not null,
    age number(3) not null,
    email varchar2(50) not null,
    role varchar2(50) not null
);

select * from member;


