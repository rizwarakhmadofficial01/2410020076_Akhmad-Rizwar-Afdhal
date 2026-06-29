package Form;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;

/**
 * FormBuku — CRUD Data Buku.
 * Layout: header navy-gold, panel kiri form, panel kanan tabel dengan join kategori.
 */
public class FormBuku extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FormBuku.class.getName());

    // ── Komponen UI ──────────────────────────────────────────────────────────
    private JTextField        txtIdBuku;
    private JTextField        txtJudul;
    private JTextField        txtPenulis;
    private JTextField        txtTahun;
    private JTextField        txtStok;
    private JComboBox<String> cmbKategori;
    private JButton           btnSimpan, btnUbah, btnHapus, btnBersih, btnKembali;
    private JTable            tabelBuku;
    private DefaultTableModel modelTabel;

    // Label-label (dipertahankan agar kompatibel dengan inspector NetBeans)
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7;
    private JScrollPane jScrollPane1;

    public FormBuku() {
        setTitle("Data Buku — Sistem Informasi Perpustakaan");
        setSize(960, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
        terapkanDesain();
        tampilKategori();
        tampilData();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG);
        setContentPane(root);

        // ── HEADER ────────────────────────────────────────────────────────
        root.add(UITheme.headerPanel("📖", "Data Buku",
            "Kelola seluruh koleksi buku perpustakaan"), BorderLayout.NORTH);

        // ── KONTEN ───────────────────────────────────────────────────────
        JPanel content = new JPanel(new BorderLayout(16, 0));
        content.setBackground(UITheme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(14, 18, 10, 18));

        // ---- PANEL KIRI: FORM -------------------------------------------
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(UITheme.WHITE);
        formCard.setBorder(UITheme.sectionBorder("Input Data Buku"));
        formCard.setPreferredSize(new Dimension(260, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; gbc.insets = new Insets(4, 8, 2, 8);

        // ID Buku
        gbc.gridy = 0;
        jLabel2 = new JLabel("ID Buku");
        formCard.add(UITheme.styledLabel("ID Buku"), gbc);
        gbc.gridy = 1; gbc.insets = new Insets(0, 8, 6, 8);
        txtIdBuku = UITheme.styledField();
        txtIdBuku.setEditable(false);
        txtIdBuku.setBackground(new Color(0xF0, 0xF2, 0xF7));
        formCard.add(txtIdBuku, gbc);

        addRow(formCard, gbc, 2, "Judul Buku",   () -> txtJudul   = UITheme.styledField());
        addRow(formCard, gbc, 4, "Penulis",       () -> txtPenulis = UITheme.styledField());
        addRow(formCard, gbc, 6, "Tahun Terbit",  () -> txtTahun   = UITheme.styledField());
        addRow(formCard, gbc, 8, "Stok",          () -> txtStok    = UITheme.styledField());

        gbc.gridy = 10; gbc.insets = new Insets(6, 8, 2, 8);
        jLabel7 = new JLabel("Kategori");
        formCard.add(UITheme.styledLabel("Kategori"), gbc);
        gbc.gridy = 11; gbc.insets = new Insets(0, 8, 10, 8);
        cmbKategori = UITheme.styledCombo();
        formCard.add(cmbKategori, gbc);

        // Tombol aksi
        gbc.insets = new Insets(4, 8, 4, 8);
        String[]  labels = {"💾 Simpan", "✏️ Ubah", "🗑️ Hapus", "🧹 Bersih"};
        Color[]   colors = {UITheme.SUCCESS, UITheme.INFO, UITheme.DANGER, UITheme.WARN};
        JButton[] btns   = new JButton[4];
        for (int i = 0; i < 4; i++) {
            btns[i] = UITheme.styledButton(labels[i], colors[i]);
            btns[i].setPreferredSize(new Dimension(210, 36));
            gbc.gridy = 12 + i;
            formCard.add(btns[i], gbc);
        }
        btnSimpan = btns[0]; btnUbah = btns[1];
        btnHapus  = btns[2]; btnBersih = btns[3];

        gbc.gridy = 16; gbc.weighty = 1.0;
        formCard.add(Box.createVerticalGlue(), gbc);

        gbc.gridy = 17; gbc.weighty = 0; gbc.insets = new Insets(6, 8, 8, 8);
        btnKembali = UITheme.styledButton("← Kembali", UITheme.NAVY);
        btnKembali.setPreferredSize(new Dimension(210, 36));
        formCard.add(btnKembali, gbc);

        content.add(formCard, BorderLayout.WEST);

        // ---- PANEL KANAN: TABEL -----------------------------------------
        JPanel tabelPanel = new JPanel(new BorderLayout());
        tabelPanel.setBackground(UITheme.BG);
        tabelPanel.setBorder(UITheme.sectionBorder("Daftar Buku"));

        modelTabel = new DefaultTableModel(
            new String[]{"ID", "Judul Buku", "Penulis", "Tahun", "Stok", "Kategori"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelBuku = new JTable(modelTabel);
        UITheme.styleTable(tabelBuku);
        tabelBuku.getColumnModel().getColumn(0).setMaxWidth(45);
        tabelBuku.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelBuku.getColumnModel().getColumn(3).setMaxWidth(65);
        tabelBuku.getColumnModel().getColumn(4).setMaxWidth(50);

        jScrollPane1 = new JScrollPane(tabelBuku);
        UITheme.styleScrollBar(jScrollPane1);
        tabelPanel.add(jScrollPane1, BorderLayout.CENTER);

        JLabel lblJml = new JLabel(" ");
        lblJml.setFont(UITheme.FONT_SMALL);
        lblJml.setForeground(UITheme.TEXT_MID);
        lblJml.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 0));
        tabelPanel.add(lblJml, BorderLayout.SOUTH);
        modelTabel.addTableModelListener(ev ->
            lblJml.setText("  Total: " + modelTabel.getRowCount() + " buku"));

        content.add(tabelPanel, BorderLayout.CENTER);
        root.add(content, BorderLayout.CENTER);
    }

    /** Helper: tambah label + field ke formCard secara DRY. */
    private void addRow(JPanel panel, GridBagConstraints gbc,
                        int startRow, String labelText, Runnable fieldInit) {
        gbc.gridy = startRow; gbc.insets = new Insets(6, 8, 2, 8);
        panel.add(UITheme.styledLabel(labelText), gbc);
        fieldInit.run();
        gbc.gridy = startRow + 1; gbc.insets = new Insets(0, 8, 4, 8);
        JTextField f = switch (labelText) {
            case "Judul Buku"   -> txtJudul;
            case "Penulis"      -> txtPenulis;
            case "Tahun Terbit" -> txtTahun;
            case "Stok"         -> txtStok;
            default -> txtJudul;
        };
        panel.add(f, gbc);
    }

    // ── STYLING TAMBAHAN ─────────────────────────────────────────────────────
    private void terapkanDesain() {
        // listener klik tabel → isi form
        tabelBuku.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelBuku.getSelectedRow() != -1) {
                int row = tabelBuku.getSelectedRow();
                txtIdBuku.setText(modelTabel.getValueAt(row, 0).toString());
                txtJudul.setText(modelTabel.getValueAt(row, 1).toString());
                txtPenulis.setText(modelTabel.getValueAt(row, 2).toString());
                txtTahun.setText(modelTabel.getValueAt(row, 3).toString());
                txtStok.setText(modelTabel.getValueAt(row, 4).toString());
                String kat = modelTabel.getValueAt(row, 5).toString();
                for (int i = 0; i < cmbKategori.getItemCount(); i++) {
                    if (cmbKategori.getItemAt(i).equals(kat)) {
                        cmbKategori.setSelectedIndex(i); break;
                    }
                }
            }
        });

        // Wiring tombol
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);
        btnUbah.addActionListener(this::btnUbahActionPerformed);
        btnHapus.addActionListener(this::btnHapusActionPerformed);
        btnBersih.addActionListener(this::btnBersihActionPerformed);
        btnKembali.addActionListener(e -> {
            new MenuUtama().setVisible(true);
            dispose();
        });
    }

    // ── DATA METHODS ─────────────────────────────────────────────────────────

    public void tampilKategori() {
        try {
            Connection conn = Koneksi.getKoneksi();
            if (conn == null) return;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM kategori ORDER BY id_kategori");
            cmbKategori.removeAllItems();
            while (rs.next()) {
                cmbKategori.addItem(rs.getString("nama_kategori"));
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat kategori: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tampilData() {
        try {
            Connection conn = Koneksi.getKoneksi();
            if (conn == null) return;
            modelTabel.setRowCount(0);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT b.id_buku, b.judul_buku, b.penulis, b.tahun_terbit, "
                + "b.stok, k.nama_kategori "
                + "FROM buku b "
                + "LEFT JOIN kategori k ON b.id_kategori = k.id_kategori "
                + "ORDER BY b.id_buku");
            while (rs.next()) {
                modelTabel.addRow(new Object[]{
                    rs.getInt("id_buku"),
                    rs.getString("judul_buku"),
                    rs.getString("penulis"),
                    rs.getInt("tahun_terbit"),
                    rs.getInt("stok"),
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
        txtIdBuku.setText(""); txtJudul.setText("");
        txtPenulis.setText(""); txtTahun.setText(""); txtStok.setText("");
        if (cmbKategori.getItemCount() > 0) cmbKategori.setSelectedIndex(0);
        tabelBuku.clearSelection();
        txtJudul.requestFocus();
    }

    private boolean validasiInput() {
        if (txtJudul.getText().trim().isEmpty()
                || txtPenulis.getText().trim().isEmpty()
                || txtTahun.getText().trim().isEmpty()
                || txtStok.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (cmbKategori.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih kategori terlebih dahulu!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtTahun.getText().trim());
            Integer.parseInt(txtStok.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Tahun Terbit dan Stok harus berupa angka!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private int getIdKategori(Connection conn, String namaKategori) throws Exception {
        PreparedStatement pst = conn.prepareStatement(
            "SELECT id_kategori FROM kategori WHERE nama_kategori=?");
        pst.setString(1, namaKategori);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? rs.getInt("id_kategori") : 0;
    }

    // ── EVENT HANDLERS ───────────────────────────────────────────────────────

    private void btnSimpanActionPerformed(ActionEvent evt) {
        if (!validasiInput()) return;
        try {
            Connection conn = Koneksi.getKoneksi();
            int idKat = getIdKategori(conn, cmbKategori.getSelectedItem().toString());
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO buku(judul_buku,penulis,tahun_terbit,stok,id_kategori) VALUES(?,?,?,?,?)");
            pst.setString(1, txtJudul.getText().trim());
            pst.setString(2, txtPenulis.getText().trim());
            pst.setInt(3, Integer.parseInt(txtTahun.getText().trim()));
            pst.setInt(4, Integer.parseInt(txtStok.getText().trim()));
            pst.setInt(5, idKat);
            pst.executeUpdate(); conn.close();
            JOptionPane.showMessageDialog(this, "✅ Buku berhasil disimpan!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilData(); bersih();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnUbahActionPerformed(ActionEvent evt) {
        if (txtIdBuku.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!",
                "Validasi", JOptionPane.WARNING_MESSAGE); return;
        }
        if (!validasiInput()) return;
        try {
            Connection conn = Koneksi.getKoneksi();
            int idKat = getIdKategori(conn, cmbKategori.getSelectedItem().toString());
            PreparedStatement pst = conn.prepareStatement(
                "UPDATE buku SET judul_buku=?,penulis=?,tahun_terbit=?,stok=?,id_kategori=? WHERE id_buku=?");
            pst.setString(1, txtJudul.getText().trim());
            pst.setString(2, txtPenulis.getText().trim());
            pst.setInt(3, Integer.parseInt(txtTahun.getText().trim()));
            pst.setInt(4, Integer.parseInt(txtStok.getText().trim()));
            pst.setInt(5, idKat);
            pst.setInt(6, Integer.parseInt(txtIdBuku.getText().trim()));
            pst.executeUpdate(); conn.close();
            JOptionPane.showMessageDialog(this, "✅ Data berhasil diubah!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilData(); bersih();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnHapusActionPerformed(ActionEvent evt) {
        if (txtIdBuku.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!",
                "Validasi", JOptionPane.WARNING_MESSAGE); return;
        }
        int ok = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus buku \"" + txtJudul.getText() + "\"?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok != JOptionPane.YES_OPTION) return;
        try {
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(
                "DELETE FROM buku WHERE id_buku=?");
            pst.setInt(1, Integer.parseInt(txtIdBuku.getText().trim()));
            pst.executeUpdate(); conn.close();
            JOptionPane.showMessageDialog(this, "✅ Data berhasil dihapus!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilData(); bersih();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnBersihActionPerformed(ActionEvent evt) { bersih(); }

    // Stub handler (kompatibilitas action listener lama)
    private void txtIdBukuActionPerformed(ActionEvent evt) {}
    private void txtJudulActionPerformed  (ActionEvent evt) {}
    private void txtPenulisActionPerformed(ActionEvent evt) {}
    private void txtTahunActionPerformed  (ActionEvent evt) {}
    private void txtStokActionPerformed   (ActionEvent evt) {}
    private void cmbKategoriActionPerformed(ActionEvent evt) {}

    public static void main(String[] args) {
        UITheme.applyLookAndFeel();
        java.awt.EventQueue.invokeLater(() -> new FormBuku().setVisible(true));
    }
}
