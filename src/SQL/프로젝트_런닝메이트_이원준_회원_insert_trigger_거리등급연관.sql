set serveroutput on;

create or replace trigger T_ȸ��_�޸��Ÿ�_���_����ȭ
before insert of �޸��Ÿ� on ȸ�� for each row
declare
begin
    select ��޹�ȣ into :NEW.��� from (select ��޹�ȣ from ��� where �Ӱ�Ÿ� <= :NEW.�޸��Ÿ� order by ��޹�ȣ) where rownum = 1;
    dbms_output.put_line('T_ȸ��_�޸��Ÿ�_���_����ȭ : ' || :NEW.ȸ��ID || ', ' || :NEW.�̸� || ', ' || :NEW.�޸��Ÿ� || ', ' || :NEW.���);
end;

insert into ȸ�� values('MEM00001', '�̿���', 23, '���α׷���', '��', '�λ걤���� �λ�����', 176, 68, 20, null, null, null);
select * from ȸ��;

delete from ȸ�� where ȸ��ID='MEM00001';