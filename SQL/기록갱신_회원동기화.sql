create or replace NONEDITIONABLE trigger T_ȸ��_�޸��Ÿ�_����ȭ
before update or insert of �޸��Ÿ� on ȸ�� for each row
declare
    v_milage_new number(38);
    v_milage_old number(38);
begin
    select ��޹�ȣ, ������� into :NEW.���, v_milage_new from (select ��޹�ȣ, ������� from ��� where �Ӱ�Ÿ� <= :NEW.�޸��Ÿ� order by ��޹�ȣ) where rownum = 1;
    select ������� into v_milage_old from ��� where ��޹�ȣ = :OLD.���;
    
    dbms_output.put_line('T_ȸ��_�޸��Ÿ�_���_����ȭ : ' || :NEW.ȸ��ID || ', ' || :NEW.�̸� || ', ' || :NEW.�޸��Ÿ� || ', ' || :NEW.���);
    
    UPDATE ũ�� set ũ��.�޸��Ÿ� = ũ��.�޸��Ÿ� + :NEW.�޸��Ÿ� - :OLD.�޸��Ÿ� where ũ��.ũ��ID = :NEW.����ũ��;
    update ũ�� set ũ��.���ϸ��� = ũ��.���ϸ��� + (v_milage_new - v_milage_old) where ũ��.ũ��ID = :NEW.����ũ��;
end;