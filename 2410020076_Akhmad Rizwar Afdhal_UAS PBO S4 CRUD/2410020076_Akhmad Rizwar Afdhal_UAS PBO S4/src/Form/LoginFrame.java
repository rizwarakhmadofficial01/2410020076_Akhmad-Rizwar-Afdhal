package Form;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import koneksi.Koneksi;

/**
 * LoginFrame — Halaman login dengan desain modern.
 * Panel kiri: ilustrasi / branding navy-gold.
 * Panel kanan: form login bersih.
 */
public class LoginFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(LoginFrame.class.getName());

    // komponen form (agar kompatibel dengan generated code style)
    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private JLabel         lblStatus;

    public LoginFrame() {
        UITheme.applyLookAndFeel();
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setTitle("Login — Sistem Informasi Perpustakaan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(780, 460);

        JPanel root = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(UITheme.BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setOpaque(false);
        setContentPane(root);

        // ── PANEL KIRI (branding) ─────────────────────────────────────────
        JPanel left = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, UITheme.NAVY, 0, getHeight(), UITheme.NAVY_LIGHT);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // pola dekoratif: lingkaran transparan
                g2.setColor(new Color(255, 255, 255, 12));
                g2.fillOval(-60, -60, 260, 260);
                g2.fillOval(getWidth() - 120, getHeight() - 120, 240, 240);

                // garis gold tipis di kanan
                g2.setColor(UITheme.GOLD);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
            }
        };
        left.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = GridBagConstraints.RELATIVE;
        gc.insets = new Insets(8, 20, 8, 20);
        gc.anchor = GridBagConstraints.CENTER;

        // ikon buku besar (unicode)
        JLabel lblIkon = new JLabel("📚");
        lblIkon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 56));
        left.add(lblIkon, gc);

        JLabel lblAppName = new JLabel("<html><center>SISTEM INFORMASI<br>PERPUSTAKAAN</center></html>",
                                       SwingConstants.CENTER);
        lblAppName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblAppName.setForeground(Color.WHITE);
        left.add(lblAppName, gc);

        JPanel divider = new JPanel();
        divider.setOpaque(false);
        divider.setPreferredSize(new Dimension(60, 3));
        divider.setBackground(UITheme.GOLD);
        divider.setOpaque(true);
        left.add(divider, gc);

        JLabel lblTagline = new JLabel(
            "<html><center><font color='#D4A917'>\"Ilmu adalah cahaya,<br>perpustakaan adalah sumbernya.\"</font></center></html>",
            SwingConstants.CENTER);
        lblTagline.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        left.add(lblTagline, gc);

        root.add(left, BorderLayout.WEST);

        // ── PANEL KANAN (form) ────────────────────────────────────────────
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(UITheme.BG);
        right.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);

        // Judul
        JLabel lblJudul = new JLabel("Selamat Datang");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblJudul.setForeground(UITheme.NAVY);
        right.add(lblJudul, gbc);

        gbc.gridy++;
        JLabel lblSub = new JLabel("Masuk untuk melanjutkan");
        lblSub.setFont(UITheme.FONT_SMALL);
        lblSub.setForeground(UITheme.TEXT_MID);
        right.add(lblSub, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(18, 0, 4, 0);
        right.add(UITheme.styledLabel("Username"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 6, 0);
        txtUsername = UITheme.styledField();
        txtUsername.setPreferredSize(new Dimension(280, 38));
        right.add(txtUsername, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 4, 0);
        right.add(UITheme.styledLabel("Password"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 6, 0);
        txtPassword = UITheme.styledPassword();
        txtPassword.setPreferredSize(new Dimension(280, 38));
        right.add(txtPassword, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 0, 0, 0);
        lblStatus = new JLabel(" ");
        lblStatus.setFont(UITheme.FONT_SMALL);
        lblStatus.setForeground(UITheme.DANGER);
        right.add(lblStatus, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(14, 0, 0, 0);
        btnLogin = UITheme.styledButton("  Masuk  ", UITheme.NAVY);
        btnLogin.setPreferredSize(new Dimension(280, 42));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        right.add(btnLogin, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 0, 0);
        JLabel lblFooter = new JLabel("© 2026 Sistem Informasi Perpustakaan",
                                      SwingConstants.CENTER);
        lblFooter.setFont(UITheme.FONT_SMALL);
        lblFooter.setForeground(UITheme.TEXT_LIGHT);
        right.add(lblFooter, gbc);

        root.add(right, BorderLayout.CENTER);

        // ── LISTENERS ─────────────────────────────────────────────────────
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        txtPassword.addActionListener(this::btnLoginActionPerformed);
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
    }

    private void btnLoginActionPerformed(ActionEvent evt) {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            lblStatus.setText("Username dan password wajib diisi.");
            return;
        }

        try {
            Connection conn = Koneksi.getKoneksi();
            if (conn == null) {
                lblStatus.setText("Gagal terhubung ke database.");
                return;
            }

            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama_lengkap");
                conn.close();
                new MenuUtama(nama).setVisible(true);
                dispose();
            } else {
                conn.close();
                lblStatus.setText("Username atau password salah.");
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    private void txtUsernameActionPerformed(ActionEvent evt) { /* navigasi ke password */ }

    public static void main(String[] args) {
        UITheme.applyLookAndFeel();
        java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    // Variables declaration (untuk kompatibilitas template NetBeans)
    // private javax.swing.JTextField     txtUsername;
    // private javax.swing.JPasswordField txtPassword;
    // private javax.swing.JButton        btnLogin;
    // private javax.swing.JLabel         lblStatus;
    // End of variables declaration
}
