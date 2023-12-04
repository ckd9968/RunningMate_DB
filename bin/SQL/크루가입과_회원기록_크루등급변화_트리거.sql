-- ũ�� ���� ���� Ʈ����

-- ���� : ���ϸ����� ���� ȸ���� ��޿����� �����´�. ȸ���� ��Ͽ� ���� ����� ��ȭ�ϸ� ���� ũ���� ���ϸ����� ����ȴ�.
create or replace trigger T_ũ�簡��
after insert or update or delete of ����ũ�� on ȸ�� for each row
declare
    v_milage NUMBER(38);
    v_dist NUMBER(38,2);
begin
    select ������� into v_milage from ��� where ��޹�ȣ = :NEW.���;
    v_dist := :NEW.�޸��Ÿ�;
    
    if (inserting or (updating and (:OLD.����ũ�� <> :NEW.����ũ��) or :OLD.����ũ�� is null)) and :NEW.����ũ�� is not null then
        update ũ�� set ũ��.�ο��� = ũ��.�ο��� + 1 where :NEW.����ũ�� = ũ��.ũ��ID;
        update ũ�� set ũ��.���ϸ��� = ũ��.���ϸ��� + v_milage where :NEW.����ũ�� = ũ��.ũ��ID;
        update ũ�� set ũ��.�Ѵ޸��Ÿ� = ũ��.�Ѵ޸��Ÿ� + v_dist where :NEW.����ũ�� = ũ��.ũ��ID;
        
    end if;
    if (deleting or (updating  and (:OLD.����ũ�� <> :NEW.����ũ��) or :NEW.����ũ�� is null)) and :OLD.����ũ�� is not null then
        update ũ�� set ũ��.�ο��� = ũ��.�ο��� - 1 where :OLD.����ũ�� = ũ��.ũ��ID;
        update ũ�� set ũ��.���ϸ��� = ũ��.���ϸ��� - v_milage where :NEW.����ũ�� = ũ��.ũ��ID;
        update ũ�� set ũ��.�Ѵ޸��Ÿ� = ũ��.�Ѵ޸��Ÿ� - v_dist where :NEW.����ũ�� = ũ��.ũ��ID;
    end if;
end;

-- ũ�� �޸� �Ÿ� ����� ���� -> ����� ����
create or replace trigger T_ũ��_�޸��Ÿ�����
before update of �Ѵ޸��Ÿ� on ũ�� for each row
declare
    v_new_rank number(38);
begin
    select ��޹�ȣ into v_new_rank from (select * from ��� where �Ӱ�Ÿ� <= :NEW.�Ѵ޸��Ÿ� order by ��޹�ȣ asc) where rownum = 1;
    :NEW.��� := v_new_rank;
end;

-- ȸ���� ����ϸ� �� �Ÿ��� ȸ���� ũ�翡 ����ž� ��.
-- ��� ���� -> ȸ�� �� �޸��Ÿ� ��ȭ
-- ����� ���� ���� : 
create or replace trigger T_ȸ��_���
before insert on ��� for each row
declare
begin
    update ȸ�� set ȸ��.�޸��Ÿ� = ȸ��.�޸��Ÿ� + :NEW.�޸��Ÿ� where ȸ��.ȸ��ID = :NEW.���ȸ��
end;


desc ȸ��;
desc ���;