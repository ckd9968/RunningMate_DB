CREATE OR REPLACE TRIGGER T_������۾� BEFORE INSERT ON ��� FOR EACH ROW
BEGIN
    update ȸ�� set �޸��Ÿ� = �޸��Ÿ� + :NEW.�޸��Ÿ� where ȸ��.ȸ��ID = :NEW.���ȸ��;
END;

-- ��� ���̺� ��Ͻ� �۵�
-- ȸ�����̺� ȸ�� ��� ����ȭ Ʈ���� ���⵿��
-- ũ�� ���̺� �޸��Ÿ� ���� Ʈ���� ���⵿��