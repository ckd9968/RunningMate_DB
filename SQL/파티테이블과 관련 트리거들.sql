desc ��Ƽ;



alter table ȸ�� rename constraint SYS_C0015029 to "������Ƽ_�ܷ�Ű";
alter table ��Ƽ���� rename constraint SYS_C0016296 to "������Ƽ_��ƼID";
select * from user_constraints where table_name = 'ȸ��';

alter table ȸ�� disable constraint ������Ƽ_�ܷ�Ű;
alter table ȸ�� enable novalidate constraint ������Ƽ_�ܷ�Ű;
alter table ��Ƽ���� disable constraint ������Ƽ_��ƼID;
alter table ��Ƽ���� enable novalidate constraint ������Ƽ_��ƼID;



create table ��Ƽ����( 
    ��ƼID varchar2(20), 
    ȸ��ID varchar2(20), 
    primary key(��ƼID, ȸ��ID), 
    foreign key(��ƼID) references ��Ƽ(��ƼID), 
    foreign key(ȸ��ID) references ȸ��(ȸ��ID)
);



-- ��Ƽ ������ ���� ������Ƽ ���̺� ���� insert�� �̿��Ѵ�.
create or replace trigger T_��Ƽ���� before insert or delete on ��Ƽ���� for each row
declare
begin
    if inserting then
        update ȸ�� set ������Ƽ = :NEW.��ƼID where ȸ��.ȸ��ID = :NEW.ȸ��ID;
    end if;
    if deleting then -- ��Ƽ ���̺� delete�� ������Ƽ�� ��� null�� �����.
        update ȸ�� set ������Ƽ = null where ȸ��.������Ƽ = :OLD.��ƼID and ȸ��.ȸ��ID = :OLD.ȸ��ID;
    end if;
end;

-- ��Ƽ ���̺� ����, ���� Ʈ����
-- ȸ�����̺� ������Ƽ update ��Ƽ ���Խ� ���̺� insert -> ȸ�����̺��� ������.
-- ��Ƽ ���� ��� -> ��Ƽ ��� ��� -> ���� -> ��Ƽ�� ���Խ�Ű�� ���.

-- �� ��Ƽ ���, ���� ��Ƽ ����
-- JDBC�� ����ؼ� ���Ἲ ���������� OFF�ϰ� �����ϱ��.
create or replace trigger T_��Ƽ����
before insert or delete on ��Ƽ for each row
declare
begin
 -- �� ��Ƽ ��Ͻ� �� �� -> ��Ƽ�� ������Ƽ ���� -> ��Ƽ���� ���̺� Ʃ�� �߰�
    case
    when inserting then
        -- ���Խÿ��� ��Ƽ�常 ���Ե� ����, �����ÿ� ���Ե� ��� �ο��� ����.
        -- ��Ƽ�� ������ ���� ��Ƽ�� ��ϵ��� ���� ���¶� ����. -> ���Ἲ ���������� ������.
        -- Ʈ���ſ��� alter�� �ȵ�. �ܷ�Ű ���������� �����ϴ� ��� �ٸ� ����� ���ؾ� ��.
        insert into ��Ƽ���� values(:NEW.��ƼID, :NEW.��Ƽ��);
        
    when deleting then
        -- ������ ���ԵǾ� �ִ� ��Ƽ������ ��� ���� ��Ŵ.
        -- ȸ�� ���̺��� on delete set null, ��Ƽ������ on delete cascade;
        update ȸ�� set ȸ��.������Ƽ = null where ȸ��.������Ƽ = :OLD.��ƼID;
        delete from ��Ƽ���� where ��ƼID = :OLD.��ƼID;
    end case;
end;


----- Ȯ�ο� DML
select * from ȸ��;

insert into ��Ƽ values('PT00001', 'MEM00001', '���õ�����', '��������', '2023/12/20');
delete from ��Ƽ;
select * from ��Ƽ����;
select * from user_constraints where CONSTRAINT_NAME = 'SYS_C0015029';
commit;

select * from ��Ƽ;

delete from ��Ƽ����;
delete from ��Ƽ;
commit;

select * from ȸ�� left outer join ũ�� on ȸ��.����ũ�� = ũ��.ũ��ID;

select * from ȸ�� where (ȸ��ID, �̸�) = ('MEM00001','������');

select * from ��Ƽ;

insert into ��Ƽ���� values('PT00001', 'MEM00002');
select * from ��Ƽ����;
select * from ȸ��;
delete from ��Ƽ;
commit;
rollback;