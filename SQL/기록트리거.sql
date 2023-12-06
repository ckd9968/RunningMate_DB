CREATE OR REPLACE TRIGGER T_기록후작업 BEFORE INSERT ON 기록 FOR EACH ROW
BEGIN
    update 회원 set 달린거리 = 달린거리 + :NEW.달린거리 where 회원.회원ID = :NEW.기록회원;
END;

-- 기록 테이블에 기록시 작동
-- 회원테이블 회원 기록 동기화 트리거 연쇄동작
-- 크루 테이블 달린거리 변경 트리거 연쇄동작