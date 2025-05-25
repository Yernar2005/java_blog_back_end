INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO users (username, email, password, role_id, is_active)
VALUES ('admin', 'admin@example.com', 'admin', 1, true) -- пароль: admin