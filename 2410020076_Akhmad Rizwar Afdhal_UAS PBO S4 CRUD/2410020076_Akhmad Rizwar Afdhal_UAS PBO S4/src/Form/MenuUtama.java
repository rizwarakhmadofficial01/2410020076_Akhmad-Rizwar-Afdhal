package Form;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * MenuUtama — Dashboard navigasi utama.
 * Desain card dengan ikon emoji besar, hover effect, dan footer info.
 */
public class MenuUtama extends JFrame {

    private String namaUser;

    public MenuUtama() {
        this("Pengguna");
    }

    public MenuUtama(String namaUser) {
        this.namaUser = namaUser;
        setTitle("Menu Utama — Sistem Informasi Perpustakaan");
        setSize(680, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        buatTampilan();
    }

    private void buatTampilan() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG);
        setContentPane(root);

        // ── HEADER ────────────────────────────────────────────────────────
        JPanel header = UITheme.headerPanel(
            "📚",
            "Sistem Informasi Perpustakaan",
            "Halo, " + namaUser + " · " + getTanggalSekarang()
        );
        root.add(header, BorderLayout.NORTH);

        // ── GRID KARTU MENU ───────────────────────────────────────────────
        JPanel cardGrid = new JPanel(new GridLayout(1, 3, 20, 0));
        cardGrid.setBackground(UITheme.BG);
        cardGrid.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        cardGrid.add(buatKartu("📖", "Data Buku",
            "Tambah, ubah, dan hapus\ndata koleksi buku",
            UITheme.INFO, e -> {
                new FormBuku().setVisible(true);
                setVisible(false);
            }));

        cardGrid.add(buatKartu("🗂️", "Data Kategori",
            "Kelola kategori buku\nyang tersedia",
            UITheme.SUCCESS, e -> {
                new FormKategori().setVisible(true);
                setVisible(false);
            }));

        cardGrid.add(buatKartu("📊", "Laporan",
            "Lihat ringkasan dan\ncetak laporan buku",
            new Color(0x7B, 0x3F, 0xA0), e -> {
                new FormLaporan().setVisible(true);
                setVisible(false);
            }));

        root.add(cardGrid, BorderLayout.CENTER);

        // ── FOOTER ────────────────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(UITheme.WHITE);
        footer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.SEPARATOR),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JLabel lblInfo = new JLabel("Sistem Informasi Perpustakaan  ·  v1.0");
        lblInfo.setFont(UITheme.FONT_SMALL);
        lblInfo.setForeground(UITheme.TEXT_LIGHT);
        footer.add(lblInfo, BorderLayout.WEST);

        JButton btnLogout = UITheme.styledButton("🚪  Logout", UITheme.DANGER);
        btnLogout.setPreferredSize(new Dimension(110, 30));
        btnLogout.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this,
                "Yakin ingin keluar?", "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (ok == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
        footer.add(btnLogout, BorderLayout.EAST);
        root.add(footer, BorderLayout.SOUTH);
    }

    /**
     * Buat kartu navigasi dengan ikon, judul, deskripsi, dan warna aksen.
     */
    private JPanel buatKartu(String ikon, String judul, String deskripsi,
                              Color aksen, ActionListener aksi) {
        JPanel card = new JPanel(new GridBagLayout()) {
            boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) {
                        hovered = true; repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                    @Override public void mouseExited(MouseEvent e) {
                        hovered = false; repaint();
                        setCursor(Cursor.getDefaultCursor());
                    }
                    @Override public void mouseClicked(MouseEvent e) { aksi.actionPerformed(null); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? aksen.brighter() : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                // garis aksen atas
                g2.setColor(aksen);
                g2.setStroke(new BasicStroke(4));
                g2.drawLine(20, 4, getWidth() - 20, 4);
                if (hovered) {
                    // shadow ringan
                    g2.setColor(new Color(0, 0, 0, 18));
                    g2.fillRoundRect(4, 6, getWidth() - 8, getHeight() - 4, 16, 16);
                }
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? aksen : UITheme.SEPARATOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(24, 16, 24, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(6, 4, 6, 4);

        JLabel lblIcon = new JLabel(ikon, SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
        gbc.gridy = 0;
        card.add(lblIcon, gbc);

        JLabel lblJudul = new JLabel(judul, SwingConstants.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblJudul.setForeground(UITheme.NAVY);
        gbc.gridy = 1;
        card.add(lblJudul, gbc);

        JLabel lblDesc = new JLabel(
            "<html><center>" + deskripsi.replace("\n", "<br>") + "</center></html>",
            SwingConstants.CENTER);
        lblDesc.setFont(UITheme.FONT_SMALL);
        lblDesc.setForeground(UITheme.TEXT_MID);
        gbc.gridy = 2;
        card.add(lblDesc, gbc);

        JButton btn = UITheme.styledButton("Buka", aksen);
        btn.setPreferredSize(new Dimension(100, 32));
        btn.addActionListener(aksi);
        gbc.gridy = 3;
        gbc.insets = new Insets(14, 20, 0, 20);
        card.add(btn, gbc);

        return card;
    }

    private String getTanggalSekarang() {
        return new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy",
               new java.util.Locale("id", "ID"))
               .format(new java.util.Date());
    }

    public static void main(String[] args) {
        UITheme.applyLookAndFeel();
        java.awt.EventQueue.invokeLater(() -> new MenuUtama("Admin").setVisible(true));
    }
}
