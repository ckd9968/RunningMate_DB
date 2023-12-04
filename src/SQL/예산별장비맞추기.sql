set serveroutput on;

create or replace procedure SP_���꿡���߱�(budget in number, category1 in varchar2, category2 in varchar2, category3 in varchar2, ����� out SYS_REFCURSOR) as
    cursor shoes is select * from ��� where ������� = '�ȭ' order by ���� DESC;
    cursor gloves is select * from ��� where ������� = '�尩' order by ���� ASC;
    cursor head_phones is select * from ��� where ������� = '�����' order by ���� ASC;
    type ��������ڵ� is record(���ID ���.���ID%TYPE, ȸ��ID ���.ȸ��ID%TYPE, ������� ���.�������%TYPE, �귣�� ���.�귣��%TYPE, ��ǰ�� ���.��ǰ��%TYPE, ���� ���.����%TYPE);
    type ����������̺� is table of ��������ڵ�;
    pr_1_eq ����������̺�;
    pr_2_eq ����������̺�;
    pr_3_eq ����������̺�;
    
    vt_�ȭƩ�� ���%ROWTYPE;
    vt_�尩Ʃ�� ���%ROWTYPE;
    vt_�����Ʃ�� ���%ROWTYPE;
    
    i_idx number(38);
    
    type NESTED_LOOP_IDX is table of number(38);
    lefts NESTED_LOOP_IDX;
    rights NESTED_LOOP_IDX;
    middles NESTED_LOOP_IDX;
    
    loop_level number(38);
    
    v_partial_price number(38);
    v_pr_1_idx number(38);
    NO_MATCH_COMB_EXCEPTION EXCEPTION;
begin
    OPEN shoes;
    OPEN gloves;
    OPEN head_phones;
    pr_1_eq := ����������̺�();
    pr_2_eq := ����������̺�();
    pr_3_eq := ����������̺�();
    
    lefts := NESTED_LOOP_IDX();
    rights := NESTED_LOOP_IDX();
    middles := NESTED_LOOP_IDX();
    
    LOOP
    FETCH shoes into vt_�ȭƩ��.���ID, vt_�ȭƩ��.ȸ��ID, vt_�ȭƩ��.�������, vt_�ȭƩ��.�귣��, vt_�ȭƩ��.��ǰ��, vt_�ȭƩ��.����;
    FETCH gloves into vt_�尩Ʃ��.���ID, vt_�尩Ʃ��.ȸ��ID, vt_�尩Ʃ��.�������, vt_�尩Ʃ��.�귣��, vt_�尩Ʃ��.��ǰ��, vt_�尩Ʃ��.����;
    FETCH head_phones into vt_�����Ʃ��.���ID, vt_�����Ʃ��.ȸ��ID, vt_�����Ʃ��.�������, vt_�����Ʃ��.�귣��, vt_�����Ʃ��.��ǰ��, vt_�����Ʃ��.����;
    exit when shoes%NOTFOUND and gloves%NOTFOUND and head_phones%NOTFOUND;
    
    if not shoes%NOTFOUND then
        begin
        case
        when category1 = 'shoes' then
            pr_1_eq.extend;
            pr_1_eq(pr_1_eq.last) := vt_�ȭƩ��;
        when category2 = 'shoes' then
            pr_2_eq.extend;
            pr_2_eq(pr_2_eq.last) := vt_�ȭƩ��;
        when category3 = 'shoes' then
            pr_3_eq.extend;
            pr_3_eq(pr_3_eq.last) := vt_�ȭƩ��;
        end case;
        end;
    end if;
    
    if not gloves%NOTFOUND then
        begin
        case
        when category1 = 'gloves' then
            pr_1_eq.extend;
            pr_1_eq(pr_1_eq.last) := vt_�尩Ʃ��;
        when category2 = 'gloves' then
            pr_2_eq.extend;
            pr_2_eq(pr_2_eq.last) := vt_�尩Ʃ��;
        when category3 = 'gloves' then
            pr_3_eq.extend;
            pr_3_eq(pr_3_eq.last) := vt_�尩Ʃ��;
        end case;
        end;
    end if;
    
    if not head_phones%NOTFOUND then
        begin
        case
        when category1 = 'head_phones' then
            pr_1_eq.extend;
            pr_1_eq(pr_1_eq.last) := vt_�����Ʃ��;
        when category2 = 'head_phones' then
            pr_2_eq.extend;
            pr_2_eq(pr_2_eq.last) := vt_�����Ʃ��;
        when category3 = 'head_phones' then
            pr_3_eq.extend;
            pr_3_eq(pr_3_eq.last) := vt_�����Ʃ��;
        end case;
        end;
    end if;
    end loop;
    
    close shoes;
    close gloves;
    close head_phones;
    
    dbms_output.put_line(pr_1_eq.count || ' ' || pr_2_eq.count || ' ' || pr_3_eq.count);
    
    lefts.extend(2);
    rights.extend(2);
    middles.extend(2);
    for i_idx in pr_1_eq.first..pr_1_eq.last loop
        v_pr_1_idx := i_idx;
        if pr_1_eq(i_idx).���� < budget then
            dbms_output.put_line('check1 : i_idx = ' || i_idx || ' pirce = ' || pr_1_eq(i_idx).����);
            lefts(1) := 1;
            rights(1) := pr_2_eq.last;
            
            -- ���� ���� �߻� ����
            while lefts(1) < rights(1) loop
                dbms_output.put_line('check2 : lefts(1) = ' || lefts(1) || ' rights(1) = ' || rights(1));
                
                middles(1) := trunc((lefts(1) + rights(1)) / 2);
                if pr_2_eq(middles(1)).���� + pr_1_eq(i_idx).���� > budget then
                    rights(1) := middles(1) - 1;
                elsif pr_2_eq(middles(1)).���� + pr_1_eq(i_idx).���� < budget then
                    lefts(1) := middles(1) + 1;
                else
                    exit;
                end if;
            end loop;
            
            dbms_output.put_line('left(1) = ' || lefts(1));
            while( lefts(1) > 0 and pr_1_eq(i_idx).���� + pr_2_eq(lefts(1)).���� >= budget) loop
                lefts(1) := lefts(1) - 1;
            end loop;
            
            if lefts(1) >= 1 and lefts(1) <= pr_2_eq.last then
                dbms_output.put_line('price = ' || pr_2_eq(lefts(1)).����);
            end if;
            
            if lefts(1) < 1 then
                lefts(1) := -1;
                continue;
            end if;
            
            
            while lefts(1) > 0 loop
                v_partial_price := pr_1_eq(i_idx).���� + pr_2_eq(lefts(1)).����;
                lefts(2) := 1;
                rights(2) := pr_3_eq.last;
            
                while lefts(2) < rights(2) loop
                    dbms_output.put_line('check3 : lefts(2) = ' || lefts(2) || ' rights(2) = ' || rights(2));
                
                    middles(2) := trunc((lefts(2) + rights(2)) / 2);
                    if pr_3_eq(middles(2)).���� + v_partial_price > budget then
                        rights(2) := middles(2) - 1;
                    elsif pr_3_eq(middles(2)).���� + v_partial_price < budget then
                        lefts(2) := middles(2) + 1;
                    else
                        exit;
                    end if;
                end loop;
            
                while lefts(2) > 0 and pr_3_eq(lefts(2)).���� + v_partial_price > budget loop
                    lefts(2) := lefts(2) - 1;
                end loop;
                
                if lefts(2) <= pr_3_eq.last and lefts(2) >= 1 then
                    dbms_output.put_line('lefts(2) = ' || lefts(2) || ', price = ' || pr_3_eq(lefts(2)).����);
                end if;
                
                if lefts(2) > 0 then
                    goto OUT_OF_COMB;
                end if;
                
                lefts(1) := lefts(1) - 1;
            end loop;  
        end if;
    end loop;
    
    raise NO_MATCH_COMB_EXCEPTION;
    
    <<OUT_OF_COMB>> -- �� ������ �̰����� jUMP
    open ����� for select * from ��� where (���ID, ȸ��ID) in ((pr_1_eq(v_pr_1_idx).���ID, pr_1_eq(v_pr_1_idx).ȸ��ID),(pr_2_eq(lefts(1)).���ID, pr_2_eq(lefts(1)).ȸ��ID),(pr_3_eq(lefts(2)).���ID, pr_3_eq(lefts(2)).ȸ��ID));
    
    exception
    when NO_MATCH_COMB_EXCEPTION then
        dbms_output.put_line('no match');
        open ����� for select 'no data' as "result" from dual;
end;

declare
    output varchar2(4000);
begin
    SP_���꿡���߱�(78000, 'shoes', 'gloves', 'head_phones', output);
end;

select * from (
(select min(����) from ��� where �������='�����') UNION
(select min(����) from ��� where �������='�尩'));
select * from ��� where �������='�����';
select 'no data' as "result" from dual;

commit;