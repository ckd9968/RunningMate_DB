set serveroutput on;

-- 반드시 아래 인서트를 수행 --
insert into 등급 values (16, '뉴비', 0, -1);
alter table 회원 modify(직업 varchar2(40));


-- 트리거 생성 --
create or replace trigger T_회원_달린거리_등급_동기화
before insert of 달린거리 on 회원 for each row
declare
begin
    select 등급번호 into :NEW.등급 from (select 등급번호 from 등급 where 임계거리 <= :NEW.달린거리 order by 등급번호) where rownum = 1;
    dbms_output.put_line('T_회원_달린거리_등급_동기화 : ' || :NEW.회원ID || ', ' || :NEW.이름 || ', ' || :NEW.달린거리 || ', ' || :NEW.등급);
end;


-- 테스트 --
insert into 회원 values('MEM00001', '이원준', 23, '프로그래머', '남', '부산광역시 부산진구', 176, 68, 20, null, null, null);
select * from 회원;

delete from 회원 where 회원ID='MEM00001';