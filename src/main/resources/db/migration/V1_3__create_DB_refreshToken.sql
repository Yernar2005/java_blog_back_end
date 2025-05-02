-- Добавляем таблицу для хранения refresh-токенов и возможности их отзыва
CREATE TABLE refresh_tokens
(
    id         UUID PRIMARY KEY,
    user_id    BIGINT    NOT NULL
        CONSTRAINT fk_refresh_user
            REFERENCES users (id)
            ON DELETE CASCADE,
    issued_at  TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked    BOOLEAN   NOT NULL DEFAULT FALSE
);