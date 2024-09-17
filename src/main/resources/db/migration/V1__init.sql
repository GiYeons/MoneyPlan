CREATE TABLE IF NOT EXISTS member
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    account   VARCHAR(50) UNIQUE NOT NULL,
    password  VARCHAR(255)       NOT NULL
);

CREATE TABLE IF NOT EXISTS refresh_token
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    token       VARCHAR(1024)     NOT NULL,
    member_id   BIGINT            NOT NULL,
    CONSTRAINT fk_member_refresh_token FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE IF NOT EXISTS category
(
    id          BIGINT auto_increment PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS budget
(
    id          BIGINT auto_increment PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    amount       INT NOT NULL,
    member_id     BIGINT NOT NULL,
    category_id     BIGINT NOT NULL,
    CONSTRAINT fk_member_budget FOREIGN KEY (member_id) REFERENCES member (id),
    CONSTRAINT fk_category_budget FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE IF NOT EXISTS expense
(
    id          BIGINT auto_increment PRIMARY KEY,
    spent_at TIMESTAMP(6) NOT NULL,
    amount       INT NOT NULL,
    memo VARCHAR(1000) NULL,
    is_total_excluded TINYINT DEFAULT 0 NOT NULL,
    member_id     BIGINT NOT NULL,
    CONSTRAINT fk_member_expense FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE IF NOT EXISTS category_average_budget
(
    id          BIGINT auto_increment PRIMARY KEY,
    average_percentage       INT NOT NULL,
    category_id     BIGINT NOT NULL,
    CONSTRAINT fk_category_category_average_budget FOREIGN KEY (category_id) REFERENCES category (id)
);