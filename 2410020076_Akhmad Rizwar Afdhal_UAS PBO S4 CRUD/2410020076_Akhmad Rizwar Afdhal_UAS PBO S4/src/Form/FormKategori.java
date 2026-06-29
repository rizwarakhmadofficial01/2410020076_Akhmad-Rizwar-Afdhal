package Form;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

/**
 * FormKategori — CRUD Kategori Buku.
 * Desain panel kiri (form input) + panel kanan (tabel data).
 */
public class FormKategori extends javax.swing.JFrame {

    // ── Field komponen ─────────────────────────────────────────────────────
    private JTextField txtId;
    private JTextField txtKategori;
    private JButton    btnSimpan, btnUbah, btnHapus, btnBersih, btnKembali;
    private JTable     tabelKategori;
    private DefaultTableModel modelTabel;

    // ── Alias untuk kompatibilitas nama lama ───────────────────────────────
    /** Alias: txtld lama → txtId */
    private JTextField txtld;

    public FormKategori() {
        setTitle("Data Kategori — Sistem Informasi Perpustakaan");
        setSize(780, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
        UITheme.applyLookAndFeel();
        tampilData();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG);
        setContentPane(root);

        // ── HEADER ──────────────────────────────────────────────────────
        root.add(UITheme.headerPanel("🗂️", "Data Kategori",
            "Kelola kategori koleksi buku perpustakaan"), BorderLayout.NORTH);

        // ── KONTEN (split: kiri=form, kanan=tabel) ──────────────────────
        JPanel content = new JPanel(new BorderLayout(16, 0));
        content.setBackground(UITheme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(16, 20, 12, 20));

        // ---- PANEL KIRI: FORM INPUT ------------------------------------
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(UITheme.WHITE);
        formCard.setBorder(UITheme.sectionBorder("Input Data"));
        formCard.setPreferredSize(new Dimension(240, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill  = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        formCard.add(UITheme.styledLabel("ID Kategori"), gbc);

        gbc.gridy = 1;
        txtId = UITheme.styledField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(0xF0, 0xF2, 0xF7));
        txtId.setToolTipText("Diisi otomatis oleh sistem");
        formCard.add(txtId, gbc);
        txtld = txtId; // alias kompatibilitas

        gbc.gridy = 2;
        gbc.insets = new Insets(12, 8, 5, 8);
        formCard.add(UITheme.styledLabel("Nama Kategori"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(5, 8, 5, 8);
        txtKategori = UITheme.styledField();
        txtKategori.setToolTipText("Contoh: Novel, Teknologi, Sejarah");
        formCard.add(txtKategori, gbc);

        // --- Tombol aksi ---
        gbc.gridy = 4; gbc.insets = new Insets(18, 8, 4, 8);
        btnSimpan = UITheme.styledButton("💾 Simpan", UITheme.SUCCESS);
        btnSimpan.setPreferredSize(new Dimension(200, 36));
        formCard.add(btnSimpan, gbc);

        gbc.gridy = 5; gbc.insets = new Insets(4, 8, 4, 8);
        btnUbah = UITheme.styledButton("✏️ Ubah", UITheme.INFO);
        btnUbah.setPreferredSize(new Dimension(200, 36));
        formCard.add(btnUbah, gbc);

        gbc.gridy = 6;
        btnHapus = UITheme.styledButton("🗑️ Hapus", UITheme.DANGER);
        btnHapus.setPreferredSize(new Dimension(200, 36));
        formCard.add(btnHapus, gbc);

        gbc.gridy = 7; gbc.insets = new Insets(4, 8, 4, 8);
        btnBersih = UITheme.styledButton("🧹 Bersih", UITheme.WARN);
        btnBersih.setPreferredSize(new Dimension(200, 36));
        formCard.add(btnBersih, gbc);

        // Spacer bawah
        gbc.gridy = 8; gbc.weighty = 1.0;
        formCard.add(Box.createVerticalGlue(), gbc);

        gbc.gridy = 9; gbc.weighty = 0;
        gbc.insets = new Insets(8, 8, 8, 8);
        btnKembali = UITheme.styledButton("← Kembali", UITheme.NAVY);
        btnKembali.setPreferredSize(new Dimension(200, 36));
        formCard.add(btnKembali, gbc);

        content.add(formCard, BorderLayout.WEST);

        // ---- PANEL KANAN: TABEL ----------------------------------------
        JPanel tabelPanel = new JPanel(new BorderLayout());
        tabelPanel.setBackground(UITheme.BG);
        tabelPanel.setBorder(UITheme.sectionBorder("Daftar Kategori"));

        modelTabel = new DefaultTableModel(
            new String[]{"ID", "Nama Kategori"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelKategori = new JTable(modelTabel);
        UITheme.styleTable(tabelKategori);
        tabelKategori.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelKategori.getColumnModel().getColumn(0).setMaxWidth(70);

        JScrollPane scroll = new JScrollPane(tabelKategori);
        UITheme.styleScrollBar(scroll);
        tabelPanel.add(scroll, BorderLayout.CENTER);

        // Label jumlah data
        JLabel lblJml = new JLabel(" ");
        lblJml.setFont(UITheme.FONT_SMALL);
        lblJml.setForeground(UITheme.TEXT_MID);
        lblJml.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 0));
        tabelPanel.add(lblJml, BorderLayout.SOUTH);

        content.add(tabelPanel, BorderLayout.CENTER);
        root.add(content, BorderLayout.CENTER);

        // ── LISTENERS ───────────────────────────────────────────────────
        tabelKategori.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelKategori.getSelectedRow() != -1) {
                int row = tabelKategori.getSelectedRow();
                txtId.setText(modelTabel.getValueAt(row, 0).toString());
                txtKategori.setText(modelTabel.getValueAt(row, 1).toString());
            }
        });

        btnSimpan.addActionListener(this::btnSimpanActionPerformed);
        btnUbah.addActionListener(this::btnUbahActionPerformed);
        btnHapus.addActionListener(this::btnHapusActionPerformed);
        btnBersih.addActionListener(this::btnBersihActionPerformed);
        btnKembali.addActionListener(e -> {
            new MenuUtama().setVisible(true);
            dispose();
        });

        // Update label jumlah tiap tampilData()
        modelTabel.addTableModelListener(ev ->
            lblJml.setText("  Total: " + modelTabel.getRowCount() + " kategori"));
    }

    // ── DATA METHODS ────────────────────────────────────────────────────────

    public void tampilData() {
        try {
            Connection conn = Koneksi.getKoneksi();
            if (conn == null) return;
            modelTabel.setRowCount(0);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM kategori ORDER BY id_kategori");
            while (rs.next()) {
                modelTabel.addRow(new Object[]{
                    rs.getInt("id_kategori"),
                    rs.getString("nama_kategori")
                });
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void bersih() {
        txtId.setText("");
        txtKategori.setText("");
        tabelKategori.clearSelection();
        txtKategori.requestFocus();
    }

    // ── EVENT HANDLERS ──────────────────────────────────────────────────────

    private void btnSimpanActionPerformed(ActionEvent evt) {
        String nama = txtKategori.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori wajib diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            txtKategori.requestFocus();
            return;
        }
        try {
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO kategori (nama_kategori) VALUES (?)");
            pst.setString(1, nama);
            pst.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(this, "✅ Kategori berhasil disimpan!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilData(); bersih();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnUbahActionPerformed(ActionEvent evt) {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Pilih data dari tabel terlebih dahulu!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nama = txtKategori.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori wajib diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(
                "UPDATE kategori SET nama_kategori=? WHERE id_kategori=?");
            pst.setString(1, nama);
            pst.setInt(2, Integer.parseInt(txtId.getText()));
            pst.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(this, "✅ Data berhasil diubah!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilData(); bersih();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnHapusActionPerformed(ActionEvent evt) {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Pilih data yang akan dihapus!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus kategori \"" + txtKategori.getText() + "\"?\n"
            + "Data buku yang memakai kategori ini mungkin terpengaruh.",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        if (ok != JOptionPane.YES_OPTION) return;
        try {
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(
                "DELETE FROM kategori WHERE id_kategori=?");
            pst.setInt(1, Integer.parseInt(txtId.getText()));
            pst.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(this, "✅ Data berhasil dihapus!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilData(); bersih();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Gagal menghapus (mungkin masih dipakai di tabel buku): " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnBersihActionPerformed(ActionEvent evt) {
        bersih();
    }

    public static void main(String[] args) {
        UITheme.applyLookAndFeel();
        java.awt.EventQueue.invokeLater(() -> new FormKategori().setVisible(true));
    }

    // Variables declaration
    // Catatan: semua komponen dideklarasikan di atas sebagai field kelas.
    // End of variables declaration
}
