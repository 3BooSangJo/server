-- 스키마 생성 (필요 시 제거)
DROP DATABASE IF EXISTS jo;
CREATE SCHEMA IF NOT EXISTS jo;
USE jo;

-- 1. Question 테이블
CREATE TABLE jo.Question (
                             id INT AUTO_INCREMENT PRIMARY KEY COMMENT '문제 ID',
                             type ENUM('multiple_choice', 'ox') NOT NULL COMMENT '문제 유형',
                             question VARCHAR(255) NOT NULL COMMENT '문제 내용',
                             options JSON DEFAULT NULL COMMENT '4지선다형 옵션 (OX는 NULL)',
                             answer VARCHAR(50) NOT NULL COMMENT '정답 (서버 전용)',
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일'
) COMMENT='퀴즈 문제 테이블';

-- 2. QuizTurn 테이블
CREATE TABLE jo.QuizTurn (
                             id INT AUTO_INCREMENT PRIMARY KEY COMMENT '퀴즈 세션 ID',
                             member_id INT NOT NULL COMMENT '사용자 ID',
                             correct_count INT DEFAULT 0 COMMENT '맞힌 문제 수',
                             reward INT DEFAULT 0 COMMENT '지급된 보상',
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '퀴즈 진행 시간'
) COMMENT='퀴즈 세션 기록 테이블';

-- 3. QuizAnswer 테이블
CREATE TABLE jo.QuizAnswer (
                               id INT AUTO_INCREMENT PRIMARY KEY COMMENT '답변 ID',
                               turn_id INT NOT NULL COMMENT '퀴즈 세션 ID',
                               question_id INT NOT NULL COMMENT '문제 ID',
                               answer VARCHAR(50) NOT NULL COMMENT '사용자 제출 답변',
                               is_correct BOOLEAN COMMENT '정답 여부',
                               FOREIGN KEY (turn_id) REFERENCES jo.QuizTurn(id) ON DELETE CASCADE,
                               FOREIGN KEY (question_id) REFERENCES jo.Question(id) ON DELETE CASCADE
) COMMENT='사용자 답변 테이블';

-- 4. Member 테이블
CREATE TABLE jo.Member (
                           id INT AUTO_INCREMENT PRIMARY KEY COMMENT '회원 ID',
                           name VARCHAR(50) NOT NULL COMMENT '회원 이름',
                           balance DECIMAL(10, 2) DEFAULT 0.00 COMMENT '잔액',
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '가입일',
                           UNIQUE KEY (name)
) COMMENT='회원 테이블';

-- 5. Transaction 테이블
CREATE TABLE jo.Transaction (
                                id INT AUTO_INCREMENT PRIMARY KEY COMMENT '거래 ID',
                                member_id INT NOT NULL COMMENT '회원 ID',
                                type ENUM('deposit', 'withdraw') NOT NULL COMMENT '거래 유형',
                                amount DECIMAL(10, 2) NOT NULL COMMENT '거래 금액',
                                source VARCHAR(50) DEFAULT NULL COMMENT '수익 출처 (예: quiz)',
                                target VARCHAR(50) DEFAULT NULL COMMENT '지출 대상 (예: character_skin)',
                                balance_after DECIMAL(10, 2) NOT NULL COMMENT '거래 후 잔액',
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '거래 일시',
                                FOREIGN KEY (member_id) REFERENCES jo.Member(id) ON DELETE CASCADE
) COMMENT='거래 기록 테이블';

-- 6. CharacterSkin 테이블
CREATE TABLE jo.CharacterSkin (
                                  id INT AUTO_INCREMENT PRIMARY KEY COMMENT '스킨 ID',
                                  name VARCHAR(50) NOT NULL COMMENT '스킨 이름',
                                  price DECIMAL(10, 2) NOT NULL COMMENT '가격',
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일'
) COMMENT='캐릭터 스킨 테이블';

-- 7. MemberSkin 테이블
CREATE TABLE jo.MemberSkin (
                               id INT AUTO_INCREMENT PRIMARY KEY COMMENT '구매 ID',
                               member_id INT NOT NULL COMMENT '회원 ID',
                               skin_id INT NOT NULL COMMENT '스킨 ID',
                               purchased_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '구매 일시',
                               FOREIGN KEY (member_id) REFERENCES jo.Member(id) ON DELETE CASCADE,
                               FOREIGN KEY (skin_id) REFERENCES jo.CharacterSkin(id) ON DELETE CASCADE
) COMMENT='회원 스킨 구매 테이블';

-- 8. Mission 테이블
CREATE TABLE jo.Mission (
                            id INT AUTO_INCREMENT PRIMARY KEY COMMENT '미션 ID',
                            duration INT NOT NULL COMMENT '미션 기간 (일)',
                            reward INT NOT NULL COMMENT '성공 보상 금액',
                            entry_fee INT NOT NULL COMMENT '참가비',
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록 일시'
) COMMENT='미션 정보 테이블';

INSERT INTO jo.Mission (duration, reward, entry_fee) VALUES
                                                         (7, 6000, 3000),
                                                         (14, 15000, 3000),
                                                         (30, 40000, 3000);
