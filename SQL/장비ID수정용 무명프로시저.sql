declare
    CURSOR ȸ�����ID is select ȸ��ID, ���ID from ��� order by ȸ��ID;
    v_ȸ��ID varchar2(40);
    v_���ID varchar2(40);
    v_����ȸ��ID varchar2(40) := '';
    ���ID_cnt number(38) := 1;
    ���ID_PLOT varchar2(40);
begin
    ���ID_PLOT := 'EQ00000';
    OPEN ȸ�����ID;
    LOOP
        FETCH ȸ�����ID INTO v_ȸ��ID, v_���ID;
        EXIT WHEN ȸ�����ID%NOTFOUND;
        
        IF v_����ȸ��ID <> v_ȸ��ID then
            ���ID_cnt := 1;
        end if;
        update ��� set ���ID = CONCAT(SUBSTR(���ID_PLOT, 1, LENGTH(���ID_PLOT) - LENGTH(TO_CHAR(���ID_cnt))) ,TO_CHAR(���ID_cnt)) where ȸ��ID = v_ȸ��ID and ���ID = v_���ID;
        v_����ȸ��ID := v_ȸ��ID;
        ���ID_cnt := ���ID_cnt + 1;
    end loop;
    close ȸ�����ID;
end;