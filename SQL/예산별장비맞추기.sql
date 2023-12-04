set serveroutput on;

create or replace procedure SP_예산에맞추기(budget in number, category1 in varchar2, category2 in varchar2, category3 in varchar2, 장비목록 out SYS_REFCURSOR) as
    cursor shoes is select * from 장비 where 장비종류 = '운동화' order by 가격 DESC;
    cursor gloves is select * from 장비 where 장비종류 = '장갑' order by 가격 ASC;
    cursor head_phones is select * from 장비 where 장비종류 = '헤드폰' order by 가격 ASC;
    type 내부장비레코드 is record(장비ID 장비.장비ID%TYPE, 회원ID 장비.회원ID%TYPE, 장비종류 장비.장비종류%TYPE, 브랜드 장비.브랜드%TYPE, 제품명 장비.제품명%TYPE, 가격 장비.가격%TYPE);
    type 내부장비테이블 is table of 내부장비레코드;
    pr_1_eq 내부장비테이블;
    pr_2_eq 내부장비테이블;
    pr_3_eq 내부장비테이블;
    
    vt_운동화튜플 장비%ROWTYPE;
    vt_장갑튜플 장비%ROWTYPE;
    vt_헤드폰튜플 장비%ROWTYPE;
    
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
    pr_1_eq := 내부장비테이블();
    pr_2_eq := 내부장비테이블();
    pr_3_eq := 내부장비테이블();
    
    lefts := NESTED_LOOP_IDX();
    rights := NESTED_LOOP_IDX();
    middles := NESTED_LOOP_IDX();
    
    LOOP
    FETCH shoes into vt_운동화튜플.장비ID, vt_운동화튜플.회원ID, vt_운동화튜플.장비종류, vt_운동화튜플.브랜드, vt_운동화튜플.제품명, vt_운동화튜플.가격;
    FETCH gloves into vt_장갑튜플.장비ID, vt_장갑튜플.회원ID, vt_장갑튜플.장비종류, vt_장갑튜플.브랜드, vt_장갑튜플.제품명, vt_장갑튜플.가격;
    FETCH head_phones into vt_헤드폰튜플.장비ID, vt_헤드폰튜플.회원ID, vt_헤드폰튜플.장비종류, vt_헤드폰튜플.브랜드, vt_헤드폰튜플.제품명, vt_헤드폰튜플.가격;
    exit when shoes%NOTFOUND and gloves%NOTFOUND and head_phones%NOTFOUND;
    
    if not shoes%NOTFOUND then
        begin
        case
        when category1 = 'shoes' then
            pr_1_eq.extend;
            pr_1_eq(pr_1_eq.last) := vt_운동화튜플;
        when category2 = 'shoes' then
            pr_2_eq.extend;
            pr_2_eq(pr_2_eq.last) := vt_운동화튜플;
        when category3 = 'shoes' then
            pr_3_eq.extend;
            pr_3_eq(pr_3_eq.last) := vt_운동화튜플;
        end case;
        end;
    end if;
    
    if not gloves%NOTFOUND then
        begin
        case
        when category1 = 'gloves' then
            pr_1_eq.extend;
            pr_1_eq(pr_1_eq.last) := vt_장갑튜플;
        when category2 = 'gloves' then
            pr_2_eq.extend;
            pr_2_eq(pr_2_eq.last) := vt_장갑튜플;
        when category3 = 'gloves' then
            pr_3_eq.extend;
            pr_3_eq(pr_3_eq.last) := vt_장갑튜플;
        end case;
        end;
    end if;
    
    if not head_phones%NOTFOUND then
        begin
        case
        when category1 = 'head_phones' then
            pr_1_eq.extend;
            pr_1_eq(pr_1_eq.last) := vt_헤드폰튜플;
        when category2 = 'head_phones' then
            pr_2_eq.extend;
            pr_2_eq(pr_2_eq.last) := vt_헤드폰튜플;
        when category3 = 'head_phones' then
            pr_3_eq.extend;
            pr_3_eq(pr_3_eq.last) := vt_헤드폰튜플;
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
        if pr_1_eq(i_idx).가격 < budget then
            dbms_output.put_line('check1 : i_idx = ' || i_idx || ' pirce = ' || pr_1_eq(i_idx).가격);
            lefts(1) := 1;
            rights(1) := pr_2_eq.last;
            
            -- 무한 루프 발생 지점
            while lefts(1) < rights(1) loop
                dbms_output.put_line('check2 : lefts(1) = ' || lefts(1) || ' rights(1) = ' || rights(1));
                
                middles(1) := trunc((lefts(1) + rights(1)) / 2);
                if pr_2_eq(middles(1)).가격 + pr_1_eq(i_idx).가격 > budget then
                    rights(1) := middles(1) - 1;
                elsif pr_2_eq(middles(1)).가격 + pr_1_eq(i_idx).가격 < budget then
                    lefts(1) := middles(1) + 1;
                else
                    exit;
                end if;
            end loop;
            
            dbms_output.put_line('left(1) = ' || lefts(1));
            while( lefts(1) > 0 and pr_1_eq(i_idx).가격 + pr_2_eq(lefts(1)).가격 >= budget) loop
                lefts(1) := lefts(1) - 1;
            end loop;
            
            if lefts(1) >= 1 and lefts(1) <= pr_2_eq.last then
                dbms_output.put_line('price = ' || pr_2_eq(lefts(1)).가격);
            end if;
            
            if lefts(1) < 1 then
                lefts(1) := -1;
                continue;
            end if;
            
            
            while lefts(1) > 0 loop
                v_partial_price := pr_1_eq(i_idx).가격 + pr_2_eq(lefts(1)).가격;
                lefts(2) := 1;
                rights(2) := pr_3_eq.last;
            
                while lefts(2) < rights(2) loop
                    dbms_output.put_line('check3 : lefts(2) = ' || lefts(2) || ' rights(2) = ' || rights(2));
                
                    middles(2) := trunc((lefts(2) + rights(2)) / 2);
                    if pr_3_eq(middles(2)).가격 + v_partial_price > budget then
                        rights(2) := middles(2) - 1;
                    elsif pr_3_eq(middles(2)).가격 + v_partial_price < budget then
                        lefts(2) := middles(2) + 1;
                    else
                        exit;
                    end if;
                end loop;
            
                while lefts(2) > 0 and pr_3_eq(lefts(2)).가격 + v_partial_price > budget loop
                    lefts(2) := lefts(2) - 1;
                end loop;
                
                if lefts(2) <= pr_3_eq.last and lefts(2) >= 1 then
                    dbms_output.put_line('lefts(2) = ' || lefts(2) || ', price = ' || pr_3_eq(lefts(2)).가격);
                end if;
                
                if lefts(2) > 0 then
                    goto OUT_OF_COMB;
                end if;
                
                lefts(1) := lefts(1) - 1;
            end loop;  
        end if;
    end loop;
    
    raise NO_MATCH_COMB_EXCEPTION;
    
    <<OUT_OF_COMB>> -- 다 맞으면 이곳으로 jUMP
    open 장비목록 for select * from 장비 where (장비ID, 회원ID) in ((pr_1_eq(v_pr_1_idx).장비ID, pr_1_eq(v_pr_1_idx).회원ID),(pr_2_eq(lefts(1)).장비ID, pr_2_eq(lefts(1)).회원ID),(pr_3_eq(lefts(2)).장비ID, pr_3_eq(lefts(2)).회원ID));
    
    exception
    when NO_MATCH_COMB_EXCEPTION then
        dbms_output.put_line('no match');
        open 장비목록 for select 'no data' as "result" from dual;
end;

declare
    output varchar2(4000);
begin
    SP_예산에맞추기(78000, 'shoes', 'gloves', 'head_phones', output);
end;

select * from (
(select min(가격) from 장비 where 장비종류='헤드폰') UNION
(select min(가격) from 장비 where 장비종류='장갑'));
select * from 장비 where 장비종류='헤드폰';
select 'no data' as "result" from dual;

commit;