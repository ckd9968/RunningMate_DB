create or replace NONEDITIONABLE trigger T_회원_달린거리_동기화
before update or insert of 달린거리 on 회원 for each row
declare
    v_milage_new number(38);
    v_milage_old number(38);
begin
    select 등급번호, 등급점수 into :NEW.등급, v_milage_new from (select 등급번호, 등급점수 from 등급 where 임계거리 <= :NEW.달린거리 order by 등급번호) where rownum = 1;
    select 등급점수 into v_milage_old from 등급 where 등급번호 = :OLD.등급;
    
    dbms_output.put_line('T_회원_달린거리_등급_동기화 : ' || :NEW.회원ID || ', ' || :NEW.이름 || ', ' || :NEW.달린거리 || ', ' || :NEW.등급);
    
    UPDATE 크루 set 크루.달린거리 = 크루.달린거리 + :NEW.달린거리 - :OLD.달린거리 where 크루.크루ID = :NEW.가입크루;
    update 크루 set 크루.마일리지 = 크루.마일리지 + (v_milage_new - v_milage_old) where 크루.크루ID = :NEW.가입크루;
end;