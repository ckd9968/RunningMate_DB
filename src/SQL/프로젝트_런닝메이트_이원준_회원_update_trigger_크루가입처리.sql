-- ȸ�� ���̺� Ʈ���� - ũ�� ���Խ� --

create or replace trigger T_ȸ��_����ũ��_ũ��_����ȭ
after update of ����ũ�� on ȸ�� for each row
declare
    rank_point ���.�������%TYPE;
begin
    -- after �̹Ƿ� ����ũ���� ���翩�θ� �˻��� �ʿ�� ����.
    dbms_output.put_line('T_ȸ��_����ũ��_ũ��_����ȭ : ũ�簡�� ó��. ȸ�� : '||:NEW.ȸ��ID || ', ����ũ�� : ' || :NEW.����ũ��);
    update ũ�� set �ο��� = �ο��� - 1 where ũ��ID = :OLD.����ũ��;
    
    select ������� into rank_point from ��� where ���.��޹�ȣ = :NEW.���;
    update ũ�� set �ο��� = �ο��� + 1 where ũ��ID = :NEW.����ũ��;
    update ũ�� set ���ϸ��� = ���ϸ��� + rank_point where ũ��ID = :NEW.����ũ��;
    update ũ�� set �Ѵ޸��Ÿ� = �Ѵ޸��Ÿ� + :NEW.�޸��Ÿ� where ũ��ID = :NEW.����ũ��;
end;

commit;