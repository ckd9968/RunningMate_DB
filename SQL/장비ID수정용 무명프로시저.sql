declare
    CURSOR 회원장비ID is select 회원ID, 장비ID from 장비 order by 회원ID;
    v_회원ID varchar2(40);
    v_장비ID varchar2(40);
    v_이전회원ID varchar2(40) := '';
    장비ID_cnt number(38) := 1;
    장비ID_PLOT varchar2(40);
begin
    장비ID_PLOT := 'EQ00000';
    OPEN 회원장비ID;
    LOOP
        FETCH 회원장비ID INTO v_회원ID, v_장비ID;
        EXIT WHEN 회원장비ID%NOTFOUND;
        
        IF v_이전회원ID <> v_회원ID then
            장비ID_cnt := 1;
        end if;
        update 장비 set 장비ID = CONCAT(SUBSTR(장비ID_PLOT, 1, LENGTH(장비ID_PLOT) - LENGTH(TO_CHAR(장비ID_cnt))) ,TO_CHAR(장비ID_cnt)) where 회원ID = v_회원ID and 장비ID = v_장비ID;
        v_이전회원ID := v_회원ID;
        장비ID_cnt := 장비ID_cnt + 1;
    end loop;
    close 회원장비ID;
end;