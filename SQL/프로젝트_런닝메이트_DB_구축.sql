-- ����� ���� (����� : system) --
alter session set "_ORACLE_SCRIPT" = true;
select * from sys.dba_users;
drop user rm cascade;

create user rm identified by rm123;
grant resource, connect, dba to rm;

-- ���׸���Ʈ DB ����(����� : rm) --
-- number �� 38�ڸ� ����. �Ǽ��� �Ҽ����Ʒ� 2�ڸ��� ����
-- ���ڿ��� varchar2�� ���. ũ�� ��� : 40, ª���� : 20


create table ȸ��(
    ȸ��ID varchar2(20), 
    �̸� varchar2(20), 
    ���� number(38), 
    ���� varchar2(20), 
    ���� varchar2(20), 
    ������ varchar2(60), 
    Ű number(38,2), 
    ������ number(38,2), 
    �޸��Ÿ� number(38,2), 
    ��� number(38), 
    ������Ƽ varchar2(20), 
    ����ũ�� varchar2(20),
    Primary key(ȸ��ID));

create table ���(
    ��Ϲ�ȣ varchar2(20),
    ���ȸ�� varchar2(20),
    �޸��Ÿ� number(38,2),
    ������� date,
    �޸����� varchar2(60),
    Primary key(��Ϲ�ȣ, ���ȸ��));

create table ũ��(
    ũ��ID varchar2(20),
    ������ varchar2(20),
    ũ���� varchar2(20),
    ũ��� varchar2(40),
    Ȱ������ varchar2(60),
    �ο��� number(38),
    ���ϸ��� number(38),
    ��� number(38),
    �Ѵ޸��Ÿ� number(38,2),
    Primary key(ũ��ID));

create table ���(
    ��޹�ȣ number(38),
    ��޸� varchar2(100),
    ������� number(38),
    �Ӱ�Ÿ� number(38),
    Primary key(��޹�ȣ));

create table ���(
    ���ID varchar2(20),
    ȸ��ID varchar2(20),
    ������� varchar2(20),
    �귣�� varchar2(20),
    ��ǰ�� varchar2(40),
    ���� number(38),
    ���ƿ� number(38),
    Primary key(���ID, ȸ��ID));

create table ��Ƽ(
    ��ƼID varchar2(20),
    ��Ƽ�� varchar2(20),
    ��Ƽ�� varchar2(40),
    ������� varchar2(60),
    ��ӽð� date,
    Primary key(��ƼID));

create table ��õ�ڽ�(
    �ڽ���ȣ varchar2(20),
    ���ȸ�� varchar2(20),
    �ּ� varchar2(60),
    ������ varchar2(20),
    ���� number(38,2),
    �湮�� number(28),
    Primary key(�ڽ���ȣ, ���ȸ��));

create table ģ��(
    ��û�� varchar2(20),
    ������ varchar2(20),
    Primary key(��û��, ������));

create table ����(
    �ڽ���ȣ varchar2(20),
    ���ȸ�� varchar2(20),
    ���� number(1),
    ����ȸ�� varchar2(20),
    Primary key(�ڽ���ȣ, ���ȸ��, ����ȸ��));

alter table ȸ�� add foreign key(���) references ���(��޹�ȣ);
alter table ȸ�� add foreign key(������Ƽ) references ��Ƽ(��ƼID);
alter table ȸ�� add foreign key(����ũ��) references ũ��(ũ��ID);

alter table ��� add foreign key(���ȸ��) references ȸ��(ȸ��ID);

alter table ũ�� add foreign key(������) references ȸ��(ȸ��ID);
alter table ũ�� add foreign key(ũ����) references ȸ��(ȸ��ID);
alter table ũ�� add foreign key(���) references ���(��޹�ȣ);

alter table ��� add foreign key(ȸ��ID) references ȸ��(ȸ��ID);

alter table ��Ƽ add foreign key(��Ƽ��) references ȸ��(ȸ��ID);

alter table ��õ�ڽ� add foreign key(���ȸ��) references ȸ��(ȸ��ID);

alter table ģ�� add foreign key(��û��) references ȸ��(ȸ��ID);
alter table ģ�� add foreign key(������) references ȸ��(ȸ��ID);

alter table ���� add foreign key(�ڽ���ȣ, ���ȸ��) references ��õ�ڽ�(�ڽ���ȣ, ���ȸ��);
alter table ���� add foreign key(����ȸ��) references ȸ��(ȸ��ID);

commit;