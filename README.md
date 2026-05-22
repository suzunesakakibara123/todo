# todo-application
 application.propertiesの6行目に、PostgreSQLパスワードを設定して頂きたく思います。

【テーブル作成時のSQL文】
CREATE TABLE todo (
    id SERIAL PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    "user" VARCHAR(100) NOT NULL,
    deadline DATE,
    created_at TIMESTAMP,
    priority BOOLEAN NOT NULL DEFAULT false,
    done BOOLEAN NOT NULL DEFAULT false
);
