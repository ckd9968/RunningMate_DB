INSERT INTO 등급 VALUES(15, '강아지 산책도 여러분 보단 많이 갑니다.', 1000, 10);
INSERT INTO 등급 VALUES(14, '마라톤 완주 성공!', 3000, 50);
INSERT INTO 등급 VALUES(13, '아프리카(대구)도착. 여권지참.', 5000, 100);
INSERT INTO 등급 VALUES(12, '성심당', 7000, 200);
INSERT INTO 등급 VALUES(11, '넌 비행기 타냐? 난 걸어간다 후쿠오카', 9000, 250);
INSERT INTO 등급 VALUES(10, '부산 시민 상경기', 11000, 300);
INSERT INTO 등급 VALUES(9, '백두산 호랑이', 12000, 400);
INSERT INTO 등급 VALUES(8, '베이징 도착했징', 13000, 500);
INSERT INTO 등급 VALUES(7, '더울땐 역시 인도사이다', 14000, 600);
INSERT INTO 등급 VALUES(6, '건너버렸다. 태평양. 도착했다. 로스앤젤레스', 16000, 800);
INSERT INTO 등급 VALUES(5, '-포레스트 검프- 미대륙 횡단', 18000, 1000);
INSERT INTO 등급 VALUES(4, '남극점엔 한국인이 뛰어다닌다. -아문센-', 19000, 1200);
INSERT INTO 등급 VALUES(3, '아직도 지구 반바퀴. 세계는 넓다.', 21000, 1500);
INSERT INTO 등급 VALUES(2, '지구가 둥글다는걸 증명한건 당신이 처음은 아닙니다.', 23000, 3000);
INSERT INTO 등급 VALUES(1, '당신은 이제 지구인이 아닙니다. -지구에서 달까지-', 25000, 5000);

select * from 등급 order by 등급번호 desc;
