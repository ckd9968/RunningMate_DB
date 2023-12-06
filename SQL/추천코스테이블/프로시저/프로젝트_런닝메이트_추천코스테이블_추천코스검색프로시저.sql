CREATE OR REPLACE PROCEDURE SP_��õ�ڽ��˻�(
    V_�Է��ּ� IN VARCHAR2,
    V_�Էº����� IN VARCHAR2,
    V_�湮��_�̻� IN NUMBER,
    V_�湮��_���� IN NUMBER,
    V_��õ�ڽ� OUT SYS_REFCURSOR
)
AS
    V_�ּ� VARCHAR2(100);
    V_������ VARCHAR2(100);
BEGIN
    V_�ּ� := V_�Է��ּ�;
    V_������ := V_�Էº�����;

    IF V_�ּ� = '��ü' THEN
        V_�ּ� := '%'; 
    END IF;

    IF V_������ = '��ü' THEN
        V_������ := '%'; 
    END IF;

    OPEN V_��õ�ڽ� FOR
        SELECT �ּ�, ������, �湮��
        FROM ��õ�ڽ�
        WHERE (V_�Է��ּ� = '��ü' OR �ּ� LIKE V_�ּ� || '%')
              AND (V_�Էº����� = '��ü' OR ������ LIKE V_������)
              AND �湮�� BETWEEN V_�湮��_�̻� AND V_�湮��_����;
END;

DECLARE
    V_�Է��ּ� VARCHAR2(60);
    V_�Էº����� VARCHAR2(20);
    V_�湮��_�̻� NUMBER;
    V_�湮��_���� NUMBER;
    V_��õ�ڽ� SYS_REFCURSOR;

    V_�ּ� ��õ�ڽ�.�ּ�%TYPE;
    V_������ ��õ�ڽ�.������%TYPE;
    V_�湮�� ��õ�ڽ�.�湮��%TYPE;
BEGIN
    SP_��õ�ڽ��˻�('����','��ü',0,500, V_��õ�ڽ�);
    
    LOOP
        FETCH V_��õ�ڽ� INTO V_�ּ�, V_������, V_�湮��;
        EXIT WHEN V_��õ�ڽ�%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('�ּ�: ' || V_�ּ� || ', ������: ' || V_������ || ', �湮��: ' || V_�湮��);
    END LOOP;

    CLOSE V_��õ�ڽ�;
END;

    
    
