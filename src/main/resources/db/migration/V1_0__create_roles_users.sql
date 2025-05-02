-- Удаление таблиц, если они существуют
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- Создание таблицы ролей
CREATE TABLE roles
(
    id   BIGSERIAL
        CONSTRAINT roles_pk
            PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);



CREATE TABLE users
(
    id         BIGSERIAL
        CONSTRAINT users_pk
            PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL
        CONSTRAINT users_email_unique
            UNIQUE,
    password   VARCHAR(80)  NOT NULL,
    role_id    BIGINT       NOT NULL
        CONSTRAINT users_roles_id_fk
            REFERENCES roles
            ON UPDATE CASCADE ON DELETE CASCADE,
    is_active  BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts
(
    id          BIGSERIAL
        CONSTRAINT posts_pk
            PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    image_path  VARCHAR(255),  -- Путь к картинке (опциональный)
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    author_id   BIGINT       NOT NULL
        CONSTRAINT posts_users_id_fk
            REFERENCES users
            ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE comments
(
    id         BIGSERIAL
        CONSTRAINT comments_pk
            PRIMARY KEY,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    post_id    BIGINT    NOT NULL
        CONSTRAINT comments_posts_id_fk
            REFERENCES posts
            ON UPDATE CASCADE ON DELETE CASCADE,
    author_id  BIGINT    NOT NULL
        CONSTRAINT comments_users_id_fk
            REFERENCES users
            ON UPDATE CASCADE ON DELETE CASCADE
);

