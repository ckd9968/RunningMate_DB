CREATE OR REPLACE PROCEDURE SP_추천코스검색(
    V_입력주소 IN VARCHAR2,
    V_입력분위기 IN VARCHAR2,
    V_방문수_이상 IN NUMBER,
    V_방문수_이하 IN NUMBER,
    V_추천코스 OUT SYS_REFCURSOR
)
AS
    V_주소 VARCHAR2(100);
    V_분위기 VARCHAR2(100);
BEGIN
    V_주소 := V_입력주소;
    V_분위기 := V_입력분위기;

    IF V_주소 = '전체' THEN
        V_주소 := '%'; 
    END IF;

    IF V_분위기 = '전체' THEN
        V_분위기 := '%'; 
    END IF;

    OPEN V_추천코스 FOR
        SELECT 주소, 분위기, 방문수
        FROM 추천코스
        WHERE (V_입력주소 = '전체' OR 주소 LIKE V_주소 || '%')
              AND (V_입력분위기 = '전체' OR 분위기 LIKE V_분위기)
              AND 방문수 BETWEEN V_방문수_이상 AND V_방문수_이하;
END;

DECLARE
    V_입력주소 VARCHAR2(60);
    V_입력분위기 VARCHAR2(20);
    V_방문수_이상 NUMBER;
    V_방문수_이하 NUMBER;
    V_추천코스 SYS_REFCURSOR;

    V_주소 추천코스.주소%TYPE;
    V_분위기 추천코스.분위기%TYPE;
    V_방문수 추천코스.방문수%TYPE;
BEGIN
    SP_추천코스검색('서울','전체',0,500, V_추천코스);
    
    LOOP
        FETCH V_추천코스 INTO V_주소, V_분위기, V_방문수;
        EXIT WHEN V_추천코스%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('주소: ' || V_주소 || ', 분위기: ' || V_분위기 || ', 방문수: ' || V_방문수);
    END LOOP;

    CLOSE V_추천코스;
END;

    
    
