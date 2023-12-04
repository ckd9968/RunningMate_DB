create or replace function FUNC_GET_LARGE_ADDR(addr varchar2) return varchar2
as
    res varchar2(60);
    idx number(38) := 1;
    sp_pos number(38);
begin
    dbms_output.put_line('hello');
    while idx < length(addr) loop
        sp_pos := instr(addr, ' ', idx);
        if sp_pos > 0 then
            begin
                res := res || substr(addr, idx, sp_pos - idx) || ' ';
                idx := sp_pos + 1;
            end;
        else
            return trim(res);
        end if;
    end loop;
end;