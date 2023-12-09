--시퀀스 권한을 부여 (RM은 사용자명)
GRANT SELECT ON seq_장비ID TO RM;

--장비는 99999개까지 생성 가능
CREATE SEQUENCE seq_장비ID    
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 99999
    NOCYCLE;
    
--기존의 장비의 장비ID를 수정하는 프로시저 (BEFORE UPDATE)
DECLARE
   counter NUMBER := 1;
BEGIN
   LOOP
    UPDATE 장비 
    SET 장비ID = 'EQ' || LPAD(seq_장비ID.NEXTVAL, 5, '0');
    EXIT WHEN counter >= 데이터갯수;
    counter := counter + 1;
   END LOOP;
END;

-- 장비 ID가 없는 뷰 생성
CREATE OR REPLACE VIEW V_장비ID없음 AS
SELECT 회원ID, 장비종류, 브랜드, 제품명, 가격
FROM 장비;

-- 새로운 장비의 장비 ID 부여하는 트리거 생성 (INSTEAD OF INSERT)
CREATE OR REPLACE TRIGGER T_장비ID없음_삽입
INSTEAD OF INSERT ON V_장비ID없음
FOR EACH ROW
DECLARE
    v_장비ID VARCHAR(20);
BEGIN
    SELECT 'EQ' || LPAD(seq_장비ID.NEXTVAL, 5, '0') INTO v_장비ID FROM DUAL;
    INSERT INTO 장비 (장비ID, 회원ID, 장비종류, 브랜드, 제품명, 가격) VALUES (v_장비ID, :NEW.회원ID, :NEW.장비종류,:NEW.브랜드,:NEW.제품명,:NEW.가격);
END;

--데이터 삽입 확인 코드
INSERT INTO V_장비ID없음(회원ID, 장비종류, 브랜드, 제품명, 가격) VALUES ('MEM00051', '운동화', '나이키','시루떡','100000');



--트리거 오류 시, 컴파일 해볼 것
ALTER TRIGGER RM.T_장비ID없음_삽입 COMPILE;
--ENABLE일 때, 정상작용
SELECT status FROM all_triggers WHERE trigger_name = 'T_장비ID없음_삽입' AND owner = 'RM';