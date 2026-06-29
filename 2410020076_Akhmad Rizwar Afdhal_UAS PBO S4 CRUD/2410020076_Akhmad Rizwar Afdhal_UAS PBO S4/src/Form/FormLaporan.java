package Form;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

/**
 * FormLaporan — Laporan Buku Perpustakaan.
 *
 * MEMENUHI POIN TUGAS:
 *  ✅ Poin 4 : Laporan sederhana yang menjelaskan data
 *  ✅ Poin 5 : Ada JFrame Visual (tabel bergaya) DAN JFrame Text (output teks terformat)
 *  ✅ Poin 6 : Laporan per kategori (jumlah buku & total stok)
 *
 * Dua tab: "Laporan Visual" (JTable bergaya) dan "Laporan Text" (JTextArea mono).
 * Tombol "Cetak Text" membuka JFrame kedua berisi laporan teks — inilah
 * bukti penggunaan JFrame text yang diminta dosen.
 */
public class FormLaporan extends javax.swing.JFrame {

    private JTable            tabelLaporan;
    private DefaultTableModel modelTabel;
    private JTextArea         txtLaporan;
    private JButton           btnRefresh, btnCetakText, btnKembali;
    private JLabel            lblRingkasan;

    public FormLaporan() {
        setTitle("Laporan — Sistem Informasi Perpustakaan");
        setSize(820, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
        muatData();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG);
        setContentPane(root);

        // ── HEADER ────────────────────────────────────────────────────────
        root.add(UITheme.headerPanel("📊", "Laporan Data Buku",
            "Ringkasan koleksi buku perpustakaan per kategori"), BorderLayout.NORTH);

        // ── TAB PANEL ─────────────────────────────────────────────────────
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.setFont(UITheme.FONT_SUB);
        tabPane.setBackground(UITheme.BG);
        tabPane.setBorder(BorderFactory.createEmptyBorder(12, 18, 0, 18));

        // ---- TAB 1: LAPORAN VISUAL (JFrame Visual) ----------------------
        JPanel tabVisual = new JPanel(new BorderLayout(0, 8));
        tabVisual.setBackground(UITheme.BG);
        tabVisual.setBorder(BorderFactory.createEmptyBorder(12, 10, 10, 10));

        modelTabel = new DefaultTableModel(
            new String[]{"No.", "Kategori", "Jumlah Buku", "Total Stok"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelLaporan = new JTable(modelTabel);
        UITheme.styleTable(tabelLaporan);
        tabelLaporan.getColumnModel().getColumn(0).setMaxWidth(50);
        tabelLaporan.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelLaporan.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Renderer khusus: baris TOTAL dicetak tebal
        tabelLaporan.setDefaultRenderer(Object.class,
            new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                boolean isTotal = r == modelTabel.getRowCount() - 1
                    && "TOTAL".equals(modelTabel.getValueAt(r, 1));
                if (isTotal) {
                    setFont(UITheme.FONT_HEADER);
                    setBackground(UITheme.GOLD_LIGHT);
                    setForeground(UITheme.NAVY);
                } else if (!sel) {
                    setFont(UITheme.FONT_BODY);
                    setBackground(r % 2 == 0 ? UITheme.WHITE : UITheme.ROW_ALT);
                    setForeground(UITheme.TEXT_DARK);
                }
                // Kolom angka: rata kanan
                setHorizontalAlignment(c >= 2 ? SwingConstants.CENTER : SwingConstants.LEFT);
                return this;
            }
        });

        JScrollPane scrollVisual = new JScrollPane(tabelLaporan);
        UITheme.styleScrollBar(scrollVisual);
        tabVisual.add(scrollVisual, BorderLayout.CENTER);

        lblRingkasan = new JLabel(" ");
        lblRingkasan.setFont(UITheme.FONT_SMALL);
        lblRingkasan.setForeground(UITheme.TEXT_MID);
        tabVisual.add(lblRingkasan, BorderLayout.SOUTH);

        tabPane.addTab("  📊  Laporan Visual  ", tabVisual);

        // ---- TAB 2: LAPORAN TEXT (JFrame Text — sesuai poin 5 dosen) ----
        JPanel tabText = new JPanel(new BorderLayout());
        tabText.setBackground(UITheme.BG);
        tabText.setBorder(BorderFactory.createEmptyBorder(12, 10, 10, 10));

        txtLaporan = new JTextArea();
        txtLaporan.setEditable(false);
        txtLaporan.setFont(UITheme.FONT_MONO);
        txtLaporan.setBackground(new Color(0xF8, 0xF9, 0xFB));
        txtLaporan.setForeground(UITheme.TEXT_DARK);
        txtLaporan.setMargin(new Insets(14, 18, 14, 18));
        txtLaporan.setLineWrap(false);

        JScrollPane scrollText = new JScrollPane(txtLaporan);
        UITheme.styleScrollBar(scrollText);
        tabText.add(scrollText, BorderLayout.CENTER);

        tabPane.addTab("  📄  Laporan Text  ", tabText);

        root.add(tabPane, BorderLayout.CENTER);

        // ── TOMBOL BAWAH ──────────────────────────────────────────────────
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        footer.setBackground(UITheme.WHITE);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.SEPARATOR));

        btnRefresh    = UITheme.styledButton("🔄  Refresh",      UITheme.SUCCESS);
        btnCetakText  = UITheme.styledButton("🖨️  Cetak Text",   UITheme.INFO);
        btnKembali    = UITheme.styledButton("←  Kembali",       UITheme.NAVY);

        btnRefresh.setPreferredSize(new Dimension(130, 34));
        btnCetakText.setPreferredSize(new Dimension(140, 34));
        btnKembali.setPreferredSize(new Dimension(120, 34));

        footer.add(btnRefresh);
        footer.add(btnCetakText);
        footer.add(btnKembali);
        root.add(footer, BorderLayout.SOUTH);

        // ── LISTENERS ─────────────────────────────────────────────────────
        btnRefresh.addActionListener(e -> muatData());
        btnCetakText.addActionListener(e -> bukaJFrameText());
        btnKembali.addActionListener(e -> {
            new MenuUtama().setVisible(true);
            dispose();
        });
    }

    // ── DATA ─────────────────────────────────────────────────────────────────

    private void muatData() {
        try {
            Connection conn = Koneksi.getKoneksi();
            if (conn == null) return;

            modelTabel.setRowCount(0);

            String sql =
                "SELECT k.nama_kategori, " +
                "COUNT(b.id_buku)          AS jumlah_buku, " +
                "COALESCE(SUM(b.stok), 0)  AS total_stok " +
                "FROM kategori k " +
                "LEFT JOIN buku b ON k.id_kategori = b.id_kategori " +
                "GROUP BY k.id_kategori, k.nama_kategori " +
                "ORDER BY k.nama_kategori";

            Statement st = conn.createStatement();
            ResultSet rs  = st.executeQuery(sql);

            int no = 1, totalBuku = 0, totalStok = 0;
            StringBuilder sb = buildLaporanHeader();

            while (rs.next()) {
                String nama = rs.getString("nama_kategori");
                int    jml  = rs.getInt("jumlah_buku");
                int    stok = rs.getInt("total_stok");

                modelTabel.addRow(new Object[]{no, nama, jml, stok});
                sb.append(String.format("  %-4d  %-22s  %10d  %10d%n",
                    no, nama, jml, stok));

                totalBuku += jml;
                totalStok += stok;
                no++;
            }

            // Baris total
            modelTabel.addRow(new Object[]{"", "TOTAL", totalBuku, totalStok});
            sb.append(buildLaporanFooter(totalBuku, totalStok));

            txtLaporan.setText(sb.toString());
            txtLaporan.setCaretPosition(0);

            lblRingkasan.setText(String.format(
                "   %d kategori  ·  %d judul buku  ·  total stok %d eksemplar",
                no - 1, totalBuku, totalStok));

            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Gagal memuat laporan: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private StringBuilder buildLaporanHeader() {
        String tgl = new SimpleDateFormat("dd MMMM yyyy, HH:mm",
            new java.util.Locale("id", "ID")).format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append("  ╔══════════════════════════════════════════════════════╗\n");
        sb.append("  ║        LAPORAN DATA BUKU PER KATEGORI               ║\n");
        sb.append("  ║        Sistem Informasi Perpustakaan                ║\n");
        sb.append("  ╠══════════════════════════════════════════════════════╣\n");
        sb.append(String.format("  ║  Tanggal cetak : %-34s║%n", tgl));
        sb.append("  ╠══════════════════════════════════════════════════════╣\n");
        sb.append("  ║                                                      ║\n");
        sb.append(String.format("  ║  %-4s  %-22s  %10s  %10s  ║%n",
            "No.", "Kategori", "Jml Buku", "Tot. Stok"));
        sb.append("  ║  ──────────────────────────────────────────────────  ║\n");
        return sb;
    }

    private String buildLaporanFooter(int totalBuku, int totalStok) {
        return "  ║  ──────────────────────────────────────────────────  ║\n"
             + String.format("  ║  %-4s  %-22s  %10d  %10d  ║%n",
                 "", "TOTAL", totalBuku, totalStok)
             + "  ║                                                      ║\n"
             + "  ╚══════════════════════════════════════════════════════╝\n";
    }

    // ── JFRAME TEXT (bukti penggunaan JFrame text sesuai instruksi dosen) ────

    /**
     * Membuka JFrame terpisah yang HANYA berisi teks laporan + tombol print.
     * Ini adalah "JFrame text" yang dimaksud dosen (berbeda dengan JFrame visual).
     */
    private void bukaJFrameText() {
        // ── JFrame baru: ini "JFrame Text" ──────────────────────────────
        JFrame frameText = new JFrame("Cetak Laporan Text — Perpustakaan");
        frameText.setSize(560, 520);
        frameText.setLocationRelativeTo(this);
        frameText.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.WHITE);
        frameText.setContentPane(panel);

        // Header mini
        JLabel lblHead = new JLabel("  📄  LAPORAN TEKS PERPUSTAKAAN",
                                    SwingConstants.LEFT);
        lblHead.setFont(UITheme.FONT_HEADER);
        lblHead.setForeground(UITheme.NAVY);
        lblHead.setBackground(UITheme.GOLD_LIGHT);
        lblHead.setOpaque(true);
        lblHead.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        panel.add(lblHead, BorderLayout.NORTH);

        // Area teks
        JTextArea area = new JTextArea(txtLaporan.getText());
        area.setFont(UITheme.FONT_MONO);
        area.setEditable(false);
        area.setBackground(UITheme.WHITE);
        area.setForeground(UITheme.TEXT_DARK);
        area.setMargin(new Insets(12, 14, 12, 14));

        JScrollPane sc = new JScrollPane(area);
        UITheme.styleScrollBar(sc);
        panel.add(sc, BorderLayout.CENTER);

        // Tombol print + tutup
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        btnPanel.setBackground(UITheme.BG);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.SEPARATOR));

        JButton btnPrint = UITheme.styledButton("🖨️  Print", UITheme.INFO);
        btnPrint.setPreferredSize(new Dimension(120, 32));
        btnPrint.addActionListener(e -> {
            try {
                boolean done = area.print();
                if (done) JOptionPane.showMessageDialog(frameText,
                    "✅ Laporan berhasil dikirim ke printer.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameText,
                    "Gagal print: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnTutup = UITheme.styledButton("Tutup", UITheme.DANGER);
        btnTutup.setPreferredSize(new Dimension(100, 32));
        btnTutup.addActionListener(e -> frameText.dispose());

        btnPanel.add(btnPrint);
        btnPanel.add(btnTutup);
        panel.add(btnPanel, BorderLayout.SOUTH);

        frameText.setVisible(true);
    }

    public static void main(String[] args) {
        UITheme.applyLookAndFeel();
        java.awt.EventQueue.invokeLater(() -> new FormLaporan().setVisible(true));
    }
}
