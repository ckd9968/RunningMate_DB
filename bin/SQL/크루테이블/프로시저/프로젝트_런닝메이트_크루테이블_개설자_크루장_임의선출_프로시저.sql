create or replace procedure PROC_REGISTER_CREW_LEADER_INIT as
    v_random_leader 회원.회원ID%TYPE;
    v_leader_addr 회원.거주지%TYPE;
    i number(38);
    num_of_rows number(38);
    
    CURSOR c_crew is select 크루ID, 크루명 from 크루 where 개설자 is null;
    crew_data 크루%rowtype;
    
begin
    dbms_output.put_line('프로시저 시작');
    
    open c_crew;
    loop
        -- 크루장, 개설자를 등록할 크루를 fetch
        fetch c_crew into crew_data.크루ID, crew_data.크루명;
        exit when c_crew%NOTFOUND;
        
        -- 멤버들 중에서 아직 크루에 가입하지 않은 멤버를 임의로 뽑는다.
        select count(*)+1 into num_of_rows from (select * from 회원 where 가입크루 is null);
        dbms_output.put_line('num_of_rows : ' || num_of_rows);
        
        SELECT 회원ID, 거주지  into v_random_leader, v_leader_addr  FROM (SELECT 회원ID, 거주지, ROW_NUMBER() OVER (ORDER BY DBMS_RANDOM.VALUE) AS rn FROM 회원 WHERE 가입크루 IS NULL) WHERE rn = 1;
        -- 그 회원을 일단 이 크루에 가입시킨다. - 트리거가 작동하여 달린거리, 마일리지, 등급을 적용한다.
        update 회원 set 회원.가입크루 = crew_data.크루ID where 회원ID = v_random_leader;
        
        dbms_output.put_line('회원ID ' || v_random_leader);
        
        -- 이 크루의 활동지역을 개설자의 활동지역과 동기화한다. 
        update 크루 set 크루.활동지역 = FUNC_GET_LARGE_ADDR(v_leader_addr), 크루.개설자 = v_random_leader, 크루.크루장 = v_random_leader where 크루ID = crew_data.크루ID;
    end loop;
    close c_crew;
end;