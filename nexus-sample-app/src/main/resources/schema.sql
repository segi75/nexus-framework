DROP TABLE IF EXISTS TB_BOARD;

CREATE TABLE TB_BOARD (
    board_id    BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY, -- 자동 증가 (MSSQL 스타일)
    title       VARCHAR(200)         NOT NULL,
    content     VARCHAR(MAX)         NOT NULL,             -- MSSQL TEXT/VARCHAR(MAX)
    writer      VARCHAR(50)          NOT NULL,
    view_count  INT                  DEFAULT 0,
    created_at  DATETIME             DEFAULT CURRENT_TIMESTAMP
);