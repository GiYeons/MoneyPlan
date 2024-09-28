INSERT IGNORE INTO member (id, account, password)
VALUES (1, 'moneyplan', '$2a$10$cAz0EmZYfqzngI46j91lX.GdJ9hlmYbMsOa5Esr0Hh.rwyfDb8dYW');

INSERT IGNORE INTO category (name)
VALUES
('식비'),
('교통'),
('주거/통신'),
('생활'),
('패션'),
('여가'),
('여행'),
('쇼핑'),
('의료'),
('교육'),
('기타');

INSERT IGNORE INTO expense (spent_at, amount, memo, is_total_excluded, member_id, category_id)
VALUES
-- 식비
('2024-09-02 19:30:00', 9000, '친구들 만나서 저녁식사 했음', false, 1, 1),
('2024-09-04 20:00:00', 3900, '편의점 샌드위치', false, 1, 1),
('2024-09-08 09:00:00', 4800, '1+1 커피 구매', false, 1, 1),
('2024-09-08 14:00:00', 3000, null, false, 1, 1),

-- 패션
('2024-09-07 17:20:00', 50000, '셔츠 2장 구매', false, 1, 5),
('2024-09-20 17:20:00', 10000, '냉장고바지 구매', false, 1, 5),

-- 여가
('2024-09-03 20:10:00', 5000, '네이버웹툰 캐시 결제', false, 1, 6),
('2024-09-05 13:20:00', 48200, '쿠로이 티켓값', false, 1, 6),
('2024-09-11 9:00:00', 35000, '방구석 뮤지컬 티켓값', false, 1, 6),

-- 기타
('2024-09-10 01:20:00', 100000, '친구한테 빌려줌', true, 1, 11);
