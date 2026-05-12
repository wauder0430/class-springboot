-- OAuth2 > script.sql

create table tblUser (
    seq number PRIMARY KEY, -- 다양한 OAuth2 프로바이더를 사용한다면 별도의 PK를 둔다.
    username varchar2(100) UNIQUE NOT NULL , -- 내부 아이디
    name varchar2(50) not null, -- 사용자명
    email varchar2(100) UNIQUE not null,
    role varchar2(50) NOT NULL , --ROLE.MEMBER
    provider varchar2(50) NOT NULL, -- 프로바이더(google, naver, kakao 등)
    providerid varchar2(100) -- 각 OAuth2 프로바이더에서 발급하는 고유 사용자 ID
);
create sequence seqUser;

select * from tblUser;