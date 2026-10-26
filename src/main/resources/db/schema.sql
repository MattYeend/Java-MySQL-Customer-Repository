/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  matthewyeend
 * Created: 26 Oct 2026
 */

CREATE DATABASE IF NOT EXISTS shop
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE shop;

CREATE TABLE IF NOT EXISTS customer (
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    full_name  VARCHAR(100)    NOT NULL,
    email      VARCHAR(254)    NOT NULL,
    created_at TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_customer_email UNIQUE (email)
) ENGINE=InnoDB;
