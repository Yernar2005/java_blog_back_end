INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO users (username, email, password, role_id, is_active)
VALUES ('admin', 'admin@example.com', '$2a$10$vYmS.d5XxSuzXlFUfuHsL.N7y5i.t8Vk8Kq.FS8p/YIgtdE3/Ajaa', 1, true), -- пароль: admin
       ('user', 'user@example.com', '$2a$10$GQY9.xz3Y6dQCeS1.XhvEeuTeKN/aGDaJZw.v/X6UvDM0xR8q4lUi', 2, true); -- пароль: user