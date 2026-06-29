-- ============================================================
--  SETUP DATABASE: db_perpustakaan
--  Jalankan file ini di HeidiSQL / phpMyAdmin / MySQL CLI
-- ============================================================

CREATE DATABASE IF NOT EXISTS db_perpustakaan
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_perpustakaan;

-- ── Tabel user (login) ──────────────────────────────────────
CREATE TABLE IF NOT EXISTS user (
    id_user      INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Data user default (admin)
INSERT IGNORE INTO user (username, password, nama_lengkap)
VALUES ('admin', 'admin123', 'Akhmad Rizwar Afdhal');

-- ── Tabel kategori ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS kategori (
    id_kategori  INT AUTO_INCREMENT PRIMARY KEY,
    nama_kategori VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

INSERT IGNORE INTO kategori (id_kategori, nama_kategori) VALUES
    (1, 'Novel'),
    (2, 'Teknologi'),
    (3, 'Sejarah');

-- ── Tabel buku (relasi ke kategori) ───────────────────────
CREATE TABLE IF NOT EXISTS buku (
    id_buku      INT AUTO_INCREMENT PRIMARY KEY,
    judul_buku   VARCHAR(150) NOT NULL,
    penulis      VARCHAR(100) NOT NULL,
    tahun_terbit INT          NOT NULL,
    stok         INT          NOT NULL DEFAULT 0,
    id_kategori  INT,
    FOREIGN KEY (id_kategori)
        REFERENCES kategori(id_kategori)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB;

-- Data sampel buku
INSERT IGNORE INTO buku (judul_buku, penulis, tahun_terbit, stok, id_kategori) VALUES
    ('Bumi Manusia',          'Pramoedya Ananta Toer', 1980, 5, 1),
    ('Laskar Pelangi',        'Andrea Hirata',         2005, 8, 1),
    ('Clean Code',            'Robert C. Martin',      2008, 3, 2),
    ('Pemrograman Java',      'Budi Sutedjo',          2015, 6, 2),
    ('Sejarah Nusantara',     'M.C. Ricklefs',         2008, 4, 3);

-- Verifikasi
SELECT 'user'     AS tabel, COUNT(*) AS jumlah FROM user
UNION ALL
SELECT 'kategori' AS tabel, COUNT(*) AS jumlah FROM kategori
UNION ALL
SELECT 'buku'     AS tabel, COUNT(*) AS jumlah FROM buku;
