--������ ������ �ο� (RM�� ����ڸ�)
GRANT SELECT ON seq_���ID TO RM;

--���� 99999������ ���� ����
CREATE SEQUENCE seq_���ID    
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 99999
    NOCYCLE;
    
--������ ����� ���ID�� �����ϴ� ���ν��� (BEFORE UPDATE)
DECLARE
   counter NUMBER := 1;
BEGIN
   LOOP
    UPDATE ��� 
    SET ���ID = 'EQ' || LPAD(seq_���ID.NEXTVAL, 5, '0');
    EXIT WHEN counter >= �����Ͱ���;
    counter := counter + 1;
   END LOOP;
END;

-- ��� ID�� ���� �� ����
CREATE OR REPLACE VIEW V_���ID���� AS
SELECT ȸ��ID, �������, �귣��, ��ǰ��, ����
FROM ���;

-- ���ο� ����� ��� ID �ο��ϴ� Ʈ���� ���� (INSTEAD OF INSERT)
CREATE OR REPLACE TRIGGER T_���ID����_����
INSTEAD OF INSERT ON V_���ID����
FOR EACH ROW
DECLARE
    v_���ID VARCHAR(20);
BEGIN
    SELECT 'EQ' || LPAD(seq_���ID.NEXTVAL, 5, '0') INTO v_���ID FROM DUAL;
    INSERT INTO ��� (���ID, ȸ��ID, �������, �귣��, ��ǰ��, ����) VALUES (v_���ID, :NEW.ȸ��ID, :NEW.�������,:NEW.�귣��,:NEW.��ǰ��,:NEW.����);
END;

--������ ���� Ȯ�� �ڵ�
INSERT INTO V_���ID����(ȸ��ID, �������, �귣��, ��ǰ��, ����) VALUES ('MEM00051', '�ȭ', '����Ű','�÷綱','100000');



--Ʈ���� ���� ��, ������ �غ� ��
ALTER TRIGGER RM.T_���ID����_���� COMPILE;
--ENABLE�� ��, �����ۿ�
SELECT status FROM all_triggers WHERE trigger_name = 'T_���ID����_����' AND owner = 'RM';