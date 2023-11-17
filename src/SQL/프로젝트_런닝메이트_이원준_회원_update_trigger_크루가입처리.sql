-- 회원 테이블 트리거 - 크루 가입시 --

create or replace trigger T_회원_가입크루_크루_동기화
after update of 가입크루 on 회원 for each row
declare
    rank_point 등급.등급점수%TYPE;
begin
    -- after 이므로 가입크루의 존재여부를 검사할 필요는 없다.
    dbms_output.put_line('T_회원_가입크루_크루_동기화 : 크루가입 처리. 회원 : '||:NEW.회원ID || ', 가입크루 : ' || :NEW.가입크루);
    update 크루 set 인원수 = 인원수 - 1 where 크루ID = :OLD.가입크루;
    
    select 등급점수 into rank_point from 등급 where 등급.등급번호 = :NEW.등급;
    update 크루 set 인원수 = 인원수 + 1 where 크루ID = :NEW.가입크루;
    update 크루 set 마일리지 = 마일리지 + rank_point where 크루ID = :NEW.가입크루;
    update 크루 set 총달린거리 = 총달린거리 + :NEW.달린거리 where 크루ID = :NEW.가입크루;
end;

commit;