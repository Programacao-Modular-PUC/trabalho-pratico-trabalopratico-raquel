CREATE DATABASE IF NOT EXISTS sistema_hospedagem CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sistema_hospedagem;

-- O Spring Boot cria e atualiza as tabelas automaticamente por causa de:
-- spring.jpa.hibernate.ddl-auto=update
-- Este arquivo serve para criar o banco manualmente caso seja necessário.

-- Usuario opcional para testes:
-- CREATE USER IF NOT EXISTS 'hospedagem'@'localhost' IDENTIFIED BY '123456';
-- GRANT ALL PRIVILEGES ON sistema_hospedagem.* TO 'hospedagem'@'localhost';
-- FLUSH PRIVILEGES;
