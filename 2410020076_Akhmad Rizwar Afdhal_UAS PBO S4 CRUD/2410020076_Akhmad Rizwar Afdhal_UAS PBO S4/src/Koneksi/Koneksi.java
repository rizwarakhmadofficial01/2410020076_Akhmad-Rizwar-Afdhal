package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Koneksi - Singleton-style database connection helper
 * Database: db_perpustakaan (MySQL via Laragon)
 */
public class Koneksi {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_perpustakaan"
                                     + "?useSSL=false&serverTimezone=Asia/Makassar";
    private static final String USER = "root";
    private static final String PASS = "";

    /** Setiap panggilan membuka koneksi baru agar tidak stale. */
    public static Connection getKoneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
        } catch (Exception e) {
            System.err.println("[Koneksi] GAGAL: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
