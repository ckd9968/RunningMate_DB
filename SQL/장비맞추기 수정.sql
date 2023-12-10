create or replace NONEDITIONABLE procedure SP_예산에맞추기(budget in number, category1 in varchar2, category2 in varchar2, category3 in varchar2, 장비목록 out SYS_REFCURSOR) as
    -- 장비 종류별 튜플을 다루는 커서
    cursor shoes is select * from 장비 where 장비종류 = '운동화' order by 가격 ASC;
    cursor gloves is select * from 장비 where 장비종류 = '장갑' order by 가격 ASC;
    cursor head_phones is select * from 장비 where 장비종류 = '헤드폰' order by 가격 ASC;
    
    -- 장비 정보를 다루기 위한 레코드와 레코드를 저장할 테이블 정의
    type 내부장비레코드 is record(장비ID 장비.장비ID%TYPE, 회원ID 장비.회원ID%TYPE, 장비종류 장비.장비종류%TYPE, 브랜드 장비.브랜드%TYPE, 제품명 장비.제품명%TYPE, 가격 장비.가격%TYPE);
    type 내부장비테이블 is table of 내부장비레코드;
    -- 위에서 부터 우선순위별 장비 목록을 저장하는 테이블
    pr_1_eq 내부장비테이블;
    pr_2_eq 내부장비테이블;
    pr_3_eq 내부장비테이블;

    -- 장비 종류별로 커서가 가리키는 데이터를 저장할 변수
    vt_운동화튜플 장비%ROWTYPE;
    vt_장갑튜플 장비%ROWTYPE;
    vt_헤드폰튜플 장비%ROWTYPE;

    -- 인덱스 변수
    i_idx number(38);

    -- 이진 탐색을 위한 인덱스들
    type NESTED_LOOP_IDX is table of number(38);
    lefts NESTED_LOOP_IDX;
    rights NESTED_LOOP_IDX;
    middles NESTED_LOOP_IDX;

    -- 이진 탐색 인덱스 테이블의 인덱스
    loop_level number(38);
    
    -- 중간 합 저장 변수
    v_partial_price number(38);
    v_pr_1_idx number(38);
    
    -- 가능 조합이 없을 경우 발생될 예외
    NO_MATCH_COMB_EXCEPTION EXCEPTION;
begin
    -- 장비 종류별로 우선순위에 따라 테이블에 저장한다.
    OPEN shoes;
    OPEN gloves;
    OPEN head_phones;
    pr_1_eq := 내부장비테이블();
    pr_2_eq := 내부장비테이블();
    pr_3_eq := 내부장비테이블();

    lefts := NESTED_LOOP_IDX();
    rights := NESTED_LOOP_IDX();
    middles := NESTED_LOOP_IDX();

    -- 종류별로 각각 우선순위 테이블에 저장하는 반복구간. 모든 커서가 마지막에 다다를 때 까지 반복
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

    -- 이진 탐색을 수행 -> 장비 종류별로 가능한한 큰 가격을 검색
    lefts.extend(2);
    rights.extend(2);
    middles.extend(2);
    
    
    -- 1번 우선순위 장비를 가장 비싼 것부터 적용시킨다.
    for i_idx in pr_1_eq.first..pr_1_eq.last loop
        v_pr_1_idx := pr_1_eq.last + 1 - i_idx;
        if pr_1_eq(i_idx).가격 < budget then
            -- 우선순위 1번 장비 가격이 예산보다 작다면 다른 장비를 나머지에 맞춘다.
            dbms_output.put_line('check1 : v_pr_1_idx = ' || v_pr_1_idx || ' pirce = ' || pr_1_eq(v_pr_1_idx).가격);
            lefts(1) := 1;
            rights(1) := pr_2_eq.last;
            -- 이진 탐색을 2번 우선순위 장비 테이블에 수행한다.
            while lefts(1) < rights(1) loop
                dbms_output.put_line('check2 : lefts(1) = ' || lefts(1) || ' rights(1) = ' || rights(1));

                middles(1) := trunc((lefts(1) + rights(1)) / 2);
                -- 가격합이 가장 예산에 근접한 장비를 찾는다.
                if pr_2_eq(middles(1)).가격 + pr_1_eq(v_pr_1_idx).가격 > budget then
                    rights(1) := middles(1) - 1;
                elsif pr_2_eq(middles(1)).가격 + pr_1_eq(v_pr_1_idx).가격 < budget then
                    lefts(1) := middles(1) + 1;
                else
                    exit;
                end if;
            end loop;

            dbms_output.put_line('left(1) = ' || lefts(1));
            -- 위 탐색의 결과 가격합이 예산보다 크고 가장 작은 조합이 되므로 예산 내로 들어올 때까지 2번 우선순위 장비 가격을 낮춘다.
            while( lefts(1) > 0 and pr_1_eq(v_pr_1_idx).가격 + pr_2_eq(lefts(1)).가격 >= budget) loop
                lefts(1) := lefts(1) - 1;
            end loop;
            
            -- 단순 디버그를 위한 출력 내용
            if lefts(1) >= 1 and lefts(1) <= pr_2_eq.last then
                dbms_output.put_line('price = ' || pr_2_eq(lefts(1)).가격);
            end if;

            -- 만약 인덱스가 범위 밖으로 나간다면 2번 우선순위 장비 선택이 불가능하므로 1번 우선순위 장비 가격을 낮추고 다시 수행한다.
            if lefts(1) < 1 then
                lefts(1) := -1;
                continue;
            end if;

            -- 3번 우선순위 장비 가격을 예산의 나머지에 맞추기 위해 이진탐색을 수행한다.
            -- 2번 우선순위 장비 가격을 한 튜플 씩 낮춰가면서 처음 3번 우선순위 장비 가격이 예산에 들어맞는 순간을 찾는다.
            -- 이 탐색 구간의 수행 횟수를 줄이기 위해 위에서 이진 탐색을 수행하였다.
            while lefts(1) > 0 loop
                v_partial_price := pr_1_eq(v_pr_1_idx).가격 + pr_2_eq(lefts(1)).가격;
                lefts(2) := 1;
                rights(2) := pr_3_eq.last;
                -- 우선순위 3번 종류의 이진탐색을 수행
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
                -- 예산에 맞을 때 까지 우선순위 3번 가격을 낮춘다.
                while lefts(2) > 0 and pr_3_eq(lefts(2)).가격 + v_partial_price > budget loop
                    lefts(2) := lefts(2) - 1;
                end loop;
                
                -- 단순 디버그를 위한 출력
                if lefts(2) <= pr_3_eq.last and lefts(2) >= 1 then
                    dbms_output.put_line('lefts(2) = ' || lefts(2) || ', price = ' || pr_3_eq(lefts(2)).가격);
                end if;

                -- 인덱스를 낮춘 결과 예산에 들어 맞는다면 아직 인덱스가 1보다 작아지지 않았다. 따라서 조합이 완성됐으므로 분기한다.
                if lefts(2) > 0 then
                    goto OUT_OF_COMB;
                end if;

                lefts(1) := lefts(1) - 1;
            end loop;  
        end if;
    end loop;
    -- 우선순위 1번 장비 모두에 대하여 수행해도 조합이 없다면 예외를 발생시킨다.
    raise NO_MATCH_COMB_EXCEPTION;

    <<OUT_OF_COMB>> -- 다 맞으면 이곳으로 jUMP
    open 장비목록 for select * from 장비 where (장비ID, 회원ID) in ((pr_1_eq(v_pr_1_idx).장비ID, pr_1_eq(v_pr_1_idx).회원ID),(pr_2_eq(lefts(1)).장비ID, pr_2_eq(lefts(1)).회원ID),(pr_3_eq(lefts(2)).장비ID, pr_3_eq(lefts(2)).회원ID));

    exception
    when NO_MATCH_COMB_EXCEPTION then
        dbms_output.put_line('no match');
        open 장비목록 for select 'no data' as "result" from dual;
end;

commit;