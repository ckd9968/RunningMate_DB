-- 파일처리용 디렉토리 객체 생성 및 권한부여 (사용자: system)--
create directory LWJ_DATA_DIRECTORY as 'D:\2023년 3학년 2학기\데이터베이스 프로그래밍\RunningMate_DB\RunningMate_DB\src\SQL\LWJ_TEXT_DATAS';
grant read on directory LWJ_DATA_DIRECTORY to rm;
grant write on directory LWJ_DATA_DIRECTORY to rm;

--