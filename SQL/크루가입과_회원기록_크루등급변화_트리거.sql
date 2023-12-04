-- 크루 가입 전용 트리거

-- 제약 : 마일리지는 오직 회원의 등급에서만 가져온다. 회원의 기록에 의해 등급이 변화하면 가입 크루의 마일리지가 변경된다.
create or replace trigger T_크루가입
after insert or update or delete of 가입크루 on 회원 for each row
declare
    v_milage NUMBER(38);
    v_dist NUMBER(38,2);
begin
    select 등급점수 into v_milage from 등급 where 등급번호 = :NEW.등급;
    v_dist := :NEW.달린거리;
    
    if (inserting or (updating and (:OLD.가입크루 <> :NEW.가입크루) or :OLD.가입크루 is null)) and :NEW.가입크루 is not null then
        update 크루 set 크루.인원수 = 크루.인원수 + 1 where :NEW.가입크루 = 크루.크루ID;
        update 크루 set 크루.마일리지 = 크루.마일리지 + v_milage where :NEW.가입크루 = 크루.크루ID;
        update 크루 set 크루.총달린거리 = 크루.총달린거리 + v_dist where :NEW.가입크루 = 크루.크루ID;
        
    end if;
    if (deleting or (updating  and (:OLD.가입크루 <> :NEW.가입크루) or :NEW.가입크루 is null)) and :OLD.가입크루 is not null then
        update 크루 set 크루.인원수 = 크루.인원수 - 1 where :OLD.가입크루 = 크루.크루ID;
        update 크루 set 크루.마일리지 = 크루.마일리지 - v_milage where :NEW.가입크루 = 크루.크루ID;
        update 크루 set 크루.총달린거리 = 크루.총달린거리 - v_dist where :NEW.가입크루 = 크루.크루ID;
    end if;
end;

-- 크루 달린 거리 변경시 동작 -> 등급의 수정
create or replace trigger T_크루_달린거리변경
before update of 총달린거리 on 크루 for each row
declare
    v_new_rank number(38);
begin
    select 등급번호 into v_new_rank from (select * from 등급 where 임계거리 <= :NEW.총달린거리 order by 등급번호 asc) where rownum = 1;
    :NEW.등급 := v_new_rank;
end;

-- 회원이 기록하면 그 거리가 회원과 크루에 적용돼야 함.
-- 기록 삽입 -> 회원 총 달린거리 변화
-- 기록의 제약 사항 : 
create or replace trigger T_회원_기록
before insert on 기록 for each row
declare
begin
    update 회원 set 회원.달린거리 = 회원.달린거리 + :NEW.달린거리 where 회원.회원ID = :NEW.기록회원
end;


desc 회원;
desc 등급;