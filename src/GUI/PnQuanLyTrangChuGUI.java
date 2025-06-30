package GUI;

import javax.swing.*;
import java.awt.*;
import MyCustom.PanelShadow;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class PnQuanLyTrangChuGUI extends JPanel {

    JPanel top, center;
    PanelShadow content[];
    String[][] getSt = {
        {"Tính chính xác", "tinhchinhxac_128px.svg", "<html>Với sự quản lý chặt chẽ <br>cho từng loại sản phẩm,<br> do đó hệ thống quản lý thực phẩm<br> sẽ đảm bảo , uy tín tính  <br>chính xác và độ tin cậy cao.</html>"},
        {"Tính bảo mật", "tinhbaomat_128px.svg", "<html>Ngăn chặn việc thiếu hụt hàng<br> hoá một cách đáng tiếc.<br> Điều này giúp tăng hiệu suất cho  <br>doanh thu và người quản lý.</html>"},
    };

    // Màu sắc
    Color BackgroundColor = new Color(240, 247, 250);
    Color PrimaryColor = new Color(0, 102, 204);
    Color AccentColor = new Color(255, 87, 34);
    Color FooterColor = new Color(33, 43, 54);

    public PnQuanLyTrangChuGUI() {
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new BorderLayout());
        this.setBackground(BackgroundColor);
        this.setPreferredSize(new Dimension(1030, 844));  // Đặt kích thước tương tự GUI Khuyến mãi

        // ===== TOP PANEL =====
        top = new JPanel();
        top.setBackground(new Color(232, 245, 255));
        top.setPreferredSize(new Dimension(1030, 120));
        top.setLayout(new BorderLayout());

        JLabel slogan = new JLabel("QUẢN LÝ CĂNG TIN UNETI", SwingConstants.CENTER);
        slogan.setFont(new Font("Arial", Font.BOLD, 30));
        slogan.setForeground(PrimaryColor);

        JPanel underline = new JPanel();
        underline.setPreferredSize(new Dimension(800, 3));
        underline.setBackground(AccentColor);

        JPanel sloganWrapper = new JPanel(new BorderLayout());
        sloganWrapper.setOpaque(false);
        sloganWrapper.add(slogan, BorderLayout.CENTER);
        sloganWrapper.add(underline, BorderLayout.SOUTH);

        top.add(sloganWrapper, BorderLayout.CENTER);
        this.add(top, BorderLayout.NORTH);

        // ===== CENTER PANEL =====
        center = new JPanel();
        center.setBackground(BackgroundColor);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        contentPanel.setBackground(BackgroundColor);

        content = new PanelShadow[getSt.length];
        for (int i = 0; i < getSt.length; i++) {
            content[i] = new PanelShadow(getSt[i][1], getSt[i][0], getSt[i][2]);
            contentPanel.add(content[i]);
        }

        center.add(Box.createVerticalStrut(30)); // Khoảng cách trên
        center.add(contentPanel);
        center.add(Box.createVerticalGlue());   // Tạo khoảng trống dưới

        this.add(center, BorderLayout.CENTER);

        // ===== FOOTER =====
        JPanel footer = new JPanel();
        footer.setBackground(FooterColor);
        footer.setPreferredSize(new Dimension(1030, 60));
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        JLabel footerText = new JLabel("© 2025 Hệ Thống Quản Lý Thực Phẩm UNETI");
        footerText.setForeground(Color.WHITE);
        footerText.setFont(new Font("Arial", Font.PLAIN, 14));
        footer.add(footerText);

        this.add(footer, BorderLayout.SOUTH);
    }
}
