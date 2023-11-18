-- ����ó���� ���丮 ��ü ���� �� ���Ѻο� (�����: system)--
create or replace directory LWJ_DATA_DIRECTORY as 'D:\RM_DB_DATA';
grant read on directory LWJ_DATA_DIRECTORY to rm;
grant write on directory LWJ_DATA_DIRECTORY to rm;

-- �������ν��� ������ --

create or replace procedure proc_member_input_by_file(name_file in varchar2, addr_file in varchar2, job_file in varchar2)
as
    type ���� is varray(2) of varchar2(20);
    
    name_stream UTL_FILE.FILE_TYPE;
    addr_stream UTL_FILE.FILE_TYPE;
    job_stream UTL_FILE.FILE_TYPE;
    
    name_buf varchar2(20);
    addr_buf varchar2(60);
    job_buf varchar2(40);
    mem_cnt number(38);
    mem_id varchar2(20);
    
    zender_map ���� := ����('��','��');
begin
    name_stream := UTL_FILE.FOPEN('LWJ_DATA_DIRECTORY', name_file, 'r');
    addr_stream := UTL_FILE.FOPEN('LWJ_DATA_DIRECTORY', addr_file, 'r');
    job_stream := UTL_FILE.FOPEN('LWJ_DATA_DIRECTORY', job_file, 'r');
    
    if not (UTL_FILE.IS_OPEN(name_stream) and UTL_FILE.IS_OPEN(addr_stream) and UTL_FILE.IS_OPEN(job_stream)) then
        raise_application_error(-20099, '������ ������ �ʾҽ��ϴ�.');
    end if;
    
    select count(*) + 1 into mem_cnt from ȸ��;
    UTL_FILE.GET_LINE(name_stream, name_buf, 1024);
    UTL_FILE.GET_LINE(addr_stream, addr_buf, 1024);
    UTL_FILE.GET_LINE(job_stream, job_buf, 1024);
    
    loop
        addr_buf := replace(addr_buf, chr(13) || chr(10), '');
        name_buf := replace(name_buf, chr(13) || chr(10), '');
        job_buf := replace(job_buf, chr(13) || chr(10), '');
        dbms_output.put_line(name_buf || ' ' || addr_buf || ' ' || job_buf);
        mem_id := substr('MEM00000', 1, 8-length(to_char(mem_cnt))) || mem_cnt;
        insert into ȸ�� values(mem_id, name_buf, trunc(DBMS_RANDOM.VALUE(15, 80+1),0), job_buf, zender_map(trunc(DBMS_RANDOM.VALUE(1,3), 0)), addr_buf, trunc(DBMS_RANDOM.VALUE(140,190),2), trunc(DBMS_RANDOM.VALUE(45, 100),2), 0, null, null, null);
        mem_cnt := mem_cnt + 1;
        
        UTL_FILE.GET_LINE(name_stream, name_buf, 1024);
        UTL_FILE.GET_LINE(addr_stream, addr_buf, 1024);
        UTL_FILE.GET_LINE(job_stream, job_buf, 1024);
    end loop;
    
    exception
    when NO_DATA_FOUND then
        UTL_FILE.FCLOSE(name_stream);
        UTL_FILE.FCLOSE(addr_stream);
        UTL_FILE.FCLOSE(job_stream);
end;


----------------------------------------------------------------------------------------------------------------
-- ���丮 ��ü ������ ���Ѻο� Ȯ�� -- 
select directory_name, directory_path from all_directories where directory_name = 'LWJ_DATA_DIRECTORY';
select * from all_tab_privs where table_name = 'LWJ_DATA_DIRECTORY';


-- ���� ���ν��� ȣ�� --
DECLARE
    name_file varchar2(100);
    addr_file varchar2(100);
    job_file varchar2(100);
BEGIN
    name_file := 'MEMBER_NAME(001-050).txt';
    addr_file := 'MEMBER_ADDRESS(001-050).txt';
    job_file := 'MEMBER_JOB(001-050).txt';
    
    proc_member_input_by_file(name_file, addr_file, job_file);
END;

select * from ȸ��;