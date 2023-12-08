desc 파티;



alter table 회원 rename constraint SYS_C0015029 to "참여파티_외래키";
alter table 파티참여 rename constraint SYS_C0016296 to "참여파티_파티ID";
select * from user_constraints where table_name = '회원';

alter table 회원 disable constraint 참여파티_외래키;
alter table 회원 enable novalidate constraint 참여파티_외래키;
alter table 파티참여 disable constraint 참여파티_파티ID;
alter table 파티참여 enable novalidate constraint 참여파티_파티ID;



create table 파티참여( 
    파티ID varchar2(20), 
    회원ID varchar2(20), 
    primary key(파티ID, 회원ID), 
    foreign key(파티ID) references 파티(파티ID), 
    foreign key(회원ID) references 회원(회원ID)
);



-- 파티 가입은 오직 참여파티 테이블에 대한 insert를 이용한다.
create or replace trigger T_파티참여 before insert or delete on 파티참여 for each row
declare
begin
    if inserting then
        update 회원 set 참여파티 = :NEW.파티ID where 회원.회원ID = :NEW.회원ID;
    end if;
    if deleting then -- 파티 테이블 delete는 가입파티원 모두 null로 만든다.
        update 회원 set 참여파티 = null where 회원.참여파티 = :OLD.파티ID and 회원.회원ID = :OLD.회원ID;
    end if;
end;

-- 파티 테이블 삽입, 삭제 트리거
-- 회원테이블 가입파티 update 파티 가입시 테이블에 insert -> 회원테이블을 수정함.
-- 파티 가입 기능 -> 파티 목록 출력 -> 선택 -> 파티에 가입시키기 기능.

-- 새 파티 등록, 기존 파티 삭제
-- JDBC를 사용해서 무결성 제약조건을 OFF하고 삽입하기로.
create or replace trigger T_파티관리
before insert or delete on 파티 for each row
declare
begin
 -- 새 파티 등록시 할 일 -> 파티장 가입파티 수정 -> 파티참여 테이블에 튜플 추가
    case
    when inserting then
        -- 가입시에는 파티장만 가입된 상태, 삭제시엔 가입된 모든 인원에 적용.
        -- 파티장 수정시 아직 파티가 등록되지 않은 상태라서 에러. -> 무결성 제약조건을 수정함.
        -- 트리거에선 alter가 안됨. 외래키 제약조건을 변경하는 대신 다른 방법을 택해야 함.
        insert into 파티참여 values(:NEW.파티ID, :NEW.파티장);
        
    when deleting then
        -- 기존에 가입되어 있던 파티원들을 모두 해제 시킴.
        -- 회원 테이블은 on delete set null, 파티참여는 on delete cascade;
        update 회원 set 회원.참여파티 = null where 회원.참여파티 = :OLD.파티ID;
        delete from 파티참여 where 파티ID = :OLD.파티ID;
    end case;
end;


----- 확인용 DML
select * from 회원;

insert into 파티 values('PT00001', 'MEM00001', '오늘도완주', '감고개공원', '2023/12/20');
delete from 파티;
select * from 파티참여;
select * from user_constraints where CONSTRAINT_NAME = 'SYS_C0015029';
commit;

select * from 파티;

delete from 파티참여;
delete from 파티;
commit;

select * from 회원 left outer join 크루 on 회원.가입크루 = 크루.크루ID;

select * from 회원 where (회원ID, 이름) = ('MEM00001','차태현');

select * from 파티;

insert into 파티참여 values('PT00001', 'MEM00002');
select * from 파티참여;
select * from 회원;
select * from 기록;
commit;
rollback;

delete from 기록;

select * from 회원;
update 회원 set 달린거리=0;
select * from 크루;