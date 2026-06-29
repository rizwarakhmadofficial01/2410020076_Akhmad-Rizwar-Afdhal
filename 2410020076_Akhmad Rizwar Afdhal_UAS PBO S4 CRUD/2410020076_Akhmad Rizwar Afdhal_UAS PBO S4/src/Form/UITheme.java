package Form;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;

/**
 * UITheme — Design system terpusat.
 * Semua warna, font, border, dan komponen dibuat di sini
 * agar tampilan seluruh form konsisten dan profesional.
 *
 * Tema: "Deep Navy & Gold" — elegan, serius, akademis.
 */
public class UITheme {

    // ── PALETTE ─────────────────────────────────────────────────────────────
    public static final Color NAVY        = new Color(0x0D, 0x1B, 0x3E); // header utama
    public static final Color NAVY_LIGHT  = new Color(0x1A, 0x2F, 0x5E); // sidebar / panel
    public static final Color GOLD        = new Color(0xD4, 0xA9, 0x17); // aksen emas
    public static final Color GOLD_LIGHT  = new Color(0xF5, 0xE6, 0xA0); // highlight baris
    public static final Color BG          = new Color(0xF2, 0xF4, 0xF8); // background utama
    public static final Color WHITE       = Color.WHITE;
    public static final Color TEXT_DARK   = new Color(0x1C, 0x1C, 0x2E);
    public static final Color TEXT_MID    = new Color(0x5A, 0x5A, 0x7A);
    public static final Color TEXT_LIGHT  = new Color(0xA0, 0xA0, 0xB8);
    public static final Color SUCCESS     = new Color(0x1A, 0x8A, 0x4A);
    public static final Color DANGER      = new Color(0xC0, 0x2A, 0x2A);
    public static final Color INFO        = new Color(0x1A, 0x6B, 0xAA);
    public static final Color WARN        = new Color(0xC8, 0x7D, 0x0A);
    public static final Color SEPARATOR   = new Color(0xD8, 0xDC, 0xEA);
    public static final Color ROW_ALT     = new Color(0xEE, 0xF1, 0xFA);

    // ── FONTS ────────────────────────────────────────────────────────────────
    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,  20);
    public static final Font FONT_SUB     = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_MONO    = new Font("Courier New", Font.PLAIN, 13);
    public static final Font FONT_BTN     = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_HEADER  = new Font("Segoe UI", Font.BOLD,  12);

    // ── BORDERS ──────────────────────────────────────────────────────────────
    public static Border fieldBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SEPARATOR, 1, true),
            BorderFactory.createEmptyBorder(5, 9, 5, 9)
        );
    }

    public static Border fieldBorderFocus() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD, 2, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        );
    }

    public static Border sectionBorder(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SEPARATOR, 1),
            "  " + title + "  ",
            TitledBorder.LEFT, TitledBorder.TOP,
            FONT_SUB, NAVY_LIGHT
        );
        return BorderFactory.createCompoundBorder(
            tb,
            BorderFactory.createEmptyBorder(6, 10, 10, 10)
        );
    }

    // ── PANEL HELPERS ────────────────────────────────────────────────────────

    /** Panel dengan gradient diagonal Navy→Navy_Light */
    public static JPanel headerPanel(String icon, String judul, String sub) {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, NAVY, getWidth(), getHeight(), NAVY_LIGHT);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // garis aksen emas di bawah
                g2.setColor(GOLD);
                g2.setStroke(new BasicStroke(3));
                g2.drawLine(0, getHeight() - 3, getWidth(), getHeight() - 3);
            }
        };
        p.setPreferredSize(new Dimension(0, 80));
        p.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        lblIcon.setForeground(GOLD);
        lblIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 14));

        JPanel txtPanel = new JPanel(new GridLayout(2, 1));
        txtPanel.setOpaque(false);
        JLabel lJudul = new JLabel(judul);
        lJudul.setFont(FONT_TITLE);
        lJudul.setForeground(WHITE);
        JLabel lSub = new JLabel(sub);
        lSub.setFont(FONT_SMALL);
        lSub.setForeground(TEXT_LIGHT);
        txtPanel.add(lJudul);
        txtPanel.add(lSub);

        p.add(lblIcon, BorderLayout.WEST);
        p.add(txtPanel, BorderLayout.CENTER);
        return p;
    }

    // ── KOMPONEN ─────────────────────────────────────────────────────────────

    public static JTextField styledField() {
        JTextField f = new JTextField();
        f.setFont(FONT_BODY);
        f.setBorder(fieldBorder());
        f.setBackground(WHITE);
        f.setForeground(TEXT_DARK);
        addFocusEffect(f);
        return f;
    }

    public static JPasswordField styledPassword() {
        JPasswordField f = new JPasswordField();
        f.setFont(FONT_BODY);
        f.setBorder(fieldBorder());
        f.setBackground(WHITE);
        f.setForeground(TEXT_DARK);
        addFocusEffect(f);
        return f;
    }

    public static JComboBox<String> styledCombo() {
        JComboBox<String> c = new JComboBox<>();
        c.setFont(FONT_BODY);
        c.setBackground(WHITE);
        c.setForeground(TEXT_DARK);
        return c;
    }

    private static void addFocusEffect(JTextField f) {
        Border normal = fieldBorder();
        Border focus  = fieldBorderFocus();
        f.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) { f.setBorder(focus); }
            @Override public void focusLost (java.awt.event.FocusEvent e) { f.setBorder(normal); }
        });
    }

    private static void addFocusEffect(JPasswordField f) {
        Border normal = fieldBorder();
        Border focus  = fieldBorderFocus();
        f.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) { f.setBorder(focus); }
            @Override public void focusLost (java.awt.event.FocusEvent e) { f.setBorder(normal); }
        });
    }

    public static JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_SUB);
        l.setForeground(TEXT_DARK);
        return l;
    }

    /**
     * Tombol utama dengan warna solid dan hover effect.
     * color: SUCCESS / DANGER / INFO / WARN / NAVY
     */
    public static JButton styledButton(String text, Color base) {
        Color hover = base.darker();
        JButton btn = new JButton(text) {
            boolean hovered = false;
            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                        hovered = true; repaint();
                    }
                    @Override public void mouseExited(java.awt.event.MouseEvent e) {
                        hovered = false; repaint();
                    }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? hover : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 36));
        return btn;
    }

    /** Terapkan tema ke JTable sekaligus tambahkan striped renderer. */
    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(28);
        table.setGridColor(SEPARATOR);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(GOLD_LIGHT);
        table.setSelectionForeground(TEXT_DARK);
        table.setFillsViewportHeight(true);

        JTableHeader hdr = table.getTableHeader();
        hdr.setFont(FONT_HEADER);
        hdr.setBackground(NAVY);
        hdr.setForeground(WHITE);
        hdr.setReorderingAllowed(false);
        hdr.setPreferredSize(new Dimension(0, 32));

        // Striped row renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                if (!sel) {
                    setBackground(r % 2 == 0 ? WHITE : ROW_ALT);
                    setForeground(TEXT_DARK);
                }
                return this;
            }
        });
    }

    /** Terapkan custom scrollbar agar sesuai tema. */
    public static void styleScrollBar(JScrollPane sp) {
        sp.setBorder(BorderFactory.createLineBorder(SEPARATOR, 1));
        sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                this.thumbColor = new Color(0xB0, 0xB8, 0xD0);
                this.trackColor = BG;
            }
            @Override protected JButton createDecreaseButton(int o) { return zeroBtn(); }
            @Override protected JButton createIncreaseButton(int o) { return zeroBtn(); }
            private JButton zeroBtn() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });
    }

    /** Terapkan Nimbus atau FlatLaf / system L&F terbaik yang tersedia. */
    public static void applyLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }
}
