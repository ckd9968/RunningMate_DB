create or replace procedure PROC_REGISTER_CREW_LEADER_INIT as
    v_random_leader ȸ��.ȸ��ID%TYPE;
    v_leader_addr ȸ��.������%TYPE;
    i number(38);
    num_of_rows number(38);
    
    CURSOR c_crew is select ũ��ID, ũ��� from ũ�� where ������ is null;
    crew_data ũ��%rowtype;
    
begin
    dbms_output.put_line('���ν��� ����');
    
    open c_crew;
    loop
        -- ũ����, �����ڸ� ����� ũ�縦 fetch
        fetch c_crew into crew_data.ũ��ID, crew_data.ũ���;
        exit when c_crew%NOTFOUND;
        
        -- ����� �߿��� ���� ũ�翡 �������� ���� ����� ���Ƿ� �̴´�.
        select count(*)+1 into num_of_rows from (select * from ȸ�� where ����ũ�� is null);
        dbms_output.put_line('num_of_rows : ' || num_of_rows);
        
        SELECT ȸ��ID, ������  into v_random_leader, v_leader_addr  FROM (SELECT ȸ��ID, ������, ROW_NUMBER() OVER (ORDER BY DBMS_RANDOM.VALUE) AS rn FROM ȸ�� WHERE ����ũ�� IS NULL) WHERE rn = 1;
        -- �� ȸ���� �ϴ� �� ũ�翡 ���Խ�Ų��. - Ʈ���Ű� �۵��Ͽ� �޸��Ÿ�, ���ϸ���, ����� �����Ѵ�.
        update ȸ�� set ȸ��.����ũ�� = crew_data.ũ��ID where ȸ��ID = v_random_leader;
        
        dbms_output.put_line('ȸ��ID ' || v_random_leader);
        
        -- �� ũ���� Ȱ�������� �������� Ȱ�������� ����ȭ�Ѵ�. 
        update ũ�� set ũ��.Ȱ������ = FUNC_GET_LARGE_ADDR(v_leader_addr), ũ��.������ = v_random_leader, ũ��.ũ���� = v_random_leader where ũ��ID = crew_data.ũ��ID;
    end loop;
    close c_crew;
end;