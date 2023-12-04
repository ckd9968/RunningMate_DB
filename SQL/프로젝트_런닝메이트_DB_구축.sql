-- 사용자 생성 (사용자 : system) --
alter session set "_ORACLE_SCRIPT" = true;
select * from sys.dba_users;
drop user rm cascade;

create user rm identified by rm123;
grant resource, connect, dba to rm;

-- 런닝메이트 DB 구축(사용자 : rm) --
-- number 는 38자리 고정. 실수는 소수점아래 2자리로 고정
-- 문자열은 varchar2만 사용. 크기 긴거 : 40, 짧은거 : 20


create table 회원(
    회원ID varchar2(20), 
    이름 varchar2(20), 
    나이 number(38), 
    직업 varchar2(20), 
    성별 varchar2(20), 
    거주지 varchar2(60), 
    키 number(38,2), 
    몸무게 number(38,2), 
    달린거리 number(38,2), 
    등급 number(38), 
    참여파티 varchar2(20), 
    가입크루 varchar2(20),
    Primary key(회원ID));

create table 기록(
    기록번호 varchar2(20),
    기록회원 varchar2(20),
    달린거리 number(38,2),
    기록일자 date,
    달린지역 varchar2(60),
    Primary key(기록번호, 기록회원));

create table 크루(
    크루ID varchar2(20),
    개설자 varchar2(20),
    크루장 varchar2(20),
    크루명 varchar2(40),
    활동지역 varchar2(60),
    인원수 number(38),
    마일리지 number(38),
    등급 number(38),
    총달린거리 number(38,2),
    Primary key(크루ID));

create table 등급(
    등급번호 number(38),
    등급명 varchar2(100),
    등급점수 number(38),
    임계거리 number(38),
    Primary key(등급번호));

create table 장비(
    장비ID varchar2(20),
    회원ID varchar2(20),
    장비종류 varchar2(20),
    브랜드 varchar2(20),
    제품명 varchar2(40),
    가격 number(38),
    좋아요 number(38),
    Primary key(장비ID, 회원ID));

create table 파티(
    파티ID varchar2(20),
    파티장 varchar2(20),
    파티명 varchar2(40),
    모임장소 varchar2(60),
    약속시간 date,
    Primary key(파티ID));

create table 추천코스(
    코스번호 varchar2(20),
    등록회원 varchar2(20),
    주소 varchar2(60),
    분위기 varchar2(20),
    별점 number(38,2),
    방문수 number(28),
    Primary key(코스번호, 등록회원));

create table 친구(
    요청자 varchar2(20),
    수신자 varchar2(20),
    Primary key(요청자, 수신자));

create table 별점(
    코스번호 varchar2(20),
    등록회원 varchar2(20),
    별점 number(1),
    누른회원 varchar2(20),
    Primary key(코스번호, 등록회원, 누른회원));

alter table 회원 add foreign key(등급) references 등급(등급번호);
alter table 회원 add foreign key(참여파티) references 파티(파티ID);
alter table 회원 add foreign key(가입크루) references 크루(크루ID);

alter table 기록 add foreign key(기록회원) references 회원(회원ID);

alter table 크루 add foreign key(개설자) references 회원(회원ID);
alter table 크루 add foreign key(크루장) references 회원(회원ID);
alter table 크루 add foreign key(등급) references 등급(등급번호);

alter table 장비 add foreign key(회원ID) references 회원(회원ID);

alter table 파티 add foreign key(파티장) references 회원(회원ID);

alter table 추천코스 add foreign key(등록회원) references 회원(회원ID);

alter table 친구 add foreign key(요청자) references 회원(회원ID);
alter table 친구 add foreign key(수신자) references 회원(회원ID);

alter table 별점 add foreign key(코스번호, 등록회원) references 추천코스(코스번호, 등록회원);
alter table 별점 add foreign key(누른회원) references 회원(회원ID);

commit;