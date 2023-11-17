create or replace procedure PROC_CREW_INPUT_BY_FILE(crew_names in varchar2)
as
    v_crew_name ũ��.ũ���%TYPE;
    name_stream UTL_FILE.FILE_TYPE;
    
    v_crew_id ũ��.ũ��ID%TYPE;
    crew_count number(38);
begin
    select count(*) + 1 into crew_count from ũ��;
    
    name_stream := UTL_FILE.FOPEN('LWJ_DATA_DIRECTORY', crew_names, 'R');
    if not(UTL_FILE.is_open(name_stream)) then
        raise_application_error(-20099, '������ ������ �ʾҽ��ϴ�.');
    end if;
    
    loop
        UTL_FILE.GET_LINE(name_stream, v_crew_name);
        v_crew_name := replace(v_crew_name, chr(13) || chr(10), '');
        
        v_crew_id := CONCAT(SUBSTR('CREW00000', 1, 9 - LENGTH(TO_CHAR(crew_count))), crew_count);
        dbms_output.put_line(v_crew_id || ' ' || v_crew_name);
        
        insert into ũ�� values(v_crew_id, null, null, v_crew_name, null, 0, 0, 16, 0);
        crew_count := crew_count + 1;
    end loop;
    
    exception
        when NO_DATA_FOUND then
            utl_file.fclose(name_stream);
end;


declare
begin
    PROC_CREW_INPUT_BY_FILE('CREW_NAMES(001-030).txt');
end;

select * from ũ��;

commit;