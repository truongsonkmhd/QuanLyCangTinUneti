package GUI;

import BUS.NhanVienBUS;
import BUS.PhanQuyenBUS;
import DTO.NhanVien;
import DTO.PhanQuyen;
import DTO.TaiKhoan;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainQuanLyGUI extends JFrame {

    public void showWindow() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    JPanel pnTitle, pnMenuLeft, pnCard, pnTrangChu, pnBanHang, pnKhuyenMai, pnNhapHang, pnSanPham, pnNhanVien, pnKhachHang, pnThongKe;
    PnQuanLyTrangChuGUI trangChuPanel; // Add this line
    PnQuanLyBanHangGUI banHangPanel;
    PnQuanLyKhuyenMaiGUI khuyenMaiPanel;
    PnQuanLyNhapHangGUI nhapHangPanel;
    PnQuanLySanPhamGUI sanPhamPanel;
    PnQuanLyNhanVienGUI nhanVienPanel;
    PnQuanLyKhachHangGUI khachHangPanel;
    PnQuanLyThongKeGUI thongKePanel;
    
    JLabel lblTenNhomQuyen, lblUsername, lblTime;
    Timer clockTimer;
    
    Color InfoBgColor = new Color(52, 58, 72); // Màu nền info cho sidebar
    
    private NhanVien nhanVien;
    private TaiKhoan taikhoan;
    
    JLabel btnClose, btnMinimize , lblTrangChu, lblBanHang, lblKhuyenMai, lblNhapHang, lblSanPham, lblNhanVien, lblKhachHang, lblThongKe;
    final Color clLeftItem = new Color(63, 74, 89);
    final Color clLeftItemHover = new Color(72, 88, 107);
    final Color clLeftItemSelected = new Color(51, 202, 187);
    ArrayList<JLabel> listMenuLeft;
    CardLayout cardMenuLeftGroup = new CardLayout();

    public MainQuanLyGUI(TaiKhoan taiKhoan) {
        this.taikhoan = taiKhoan;
        this.setSize(1280, 850);
        Image icon = Toolkit.getDefaultToolkit().getImage("image/ManagerUI/icon-app.png");
        this.setIconImage(icon);
        nhanVien = NhanVienBUS.getInstance().getNhanVienByMaNV(taikhoan.getMaNhanVien());
        addControls();
        addEvents();
        startClock(); 
    }
    
    private void addControls() {
        int width = this.getWidth();
        int height = this.getHeight();

        Container con = getContentPane();

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BorderLayout());

        /*
        ============================================================
                                TITLE BAR
        ============================================================
         */
        pnTitle = new JPanel(null);
        pnTitle.setPreferredSize(new Dimension(width, 46));
        pnTitle.setBackground(new Color(242, 153, 74));

        btnMinimize = new JLabel(new ImageIcon("image/ManagerUI/btn-minimize.png"));
        btnMinimize.setBounds(width - 85, 5, 38, 35);
        btnMinimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pnTitle.add(btnMinimize);

        btnClose = new JLabel(new ImageIcon("image/ManagerUI/btn-close.png"));
        btnClose.setBounds(width - 40, 5, 35, 35);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pnTitle.add(btnClose);

        pnMain.add(pnTitle, BorderLayout.NORTH);

        /*
        ============================================================
                                SIDE BAR MENU WITH INFO
        ============================================================
         */
        pnMenuLeft = new JPanel();
        pnMenuLeft.setPreferredSize(new Dimension(250, height - pnTitle.getHeight()));
        pnMenuLeft.setBackground(clLeftItem);
        pnMenuLeft.setLayout(new BorderLayout());

        // ========== PANEL THÔNG TIN NHÂN VIÊN Ở ĐẦU SIDEBAR ==========
        JPanel pnUserInfo = new JPanel();
        pnUserInfo.setPreferredSize(new Dimension(250, 180));
        pnUserInfo.setBackground(InfoBgColor);
        pnUserInfo.setLayout(new BorderLayout());
        pnUserInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(51, 202, 187)));

        createSidebarInfoPanel(pnUserInfo);
        pnMenuLeft.add(pnUserInfo, BorderLayout.NORTH);

        // ========== PANEL MENU ITEMS Ở GIỮA ==========
        JPanel pnMenuItems = new JPanel();
        pnMenuItems.setBackground(clLeftItem);
        pnMenuItems.setLayout(new BoxLayout(pnMenuItems, BoxLayout.Y_AXIS));

        lblTrangChu = new JLabel(new ImageIcon("image/ManagerUI/lblTrangChu.png")); // Update icon path as needed
        lblBanHang = new JLabel(new ImageIcon("image/ManagerUI/lblBanHang.png"));
        lblKhuyenMai = new JLabel(new ImageIcon("image/ManagerUI/lblKhuyenMai.png"));
        lblNhapHang = new JLabel(new ImageIcon("image/ManagerUI/lblNhapHang.png"));
        lblSanPham = new JLabel(new ImageIcon("image/ManagerUI/lblSanPham.png"));
        lblNhanVien = new JLabel(new ImageIcon("image/ManagerUI/lblNhanVien.png"));
        lblKhachHang = new JLabel(new ImageIcon("image/ManagerUI/lblKhachHang.png"));
        lblThongKe = new JLabel(new ImageIcon("image/ManagerUI/lblThongKe.png"));

        listMenuLeft = new ArrayList<>();
        listMenuLeft.add(lblTrangChu);
        listMenuLeft.add(lblBanHang);
        listMenuLeft.add(lblKhuyenMai);
        listMenuLeft.add(lblSanPham);
        listMenuLeft.add(lblNhanVien);
        listMenuLeft.add(lblKhachHang);
        listMenuLeft.add(lblNhapHang);
        listMenuLeft.add(lblThongKe);

        for (JLabel lbl : listMenuLeft) {
            lbl.setVisible(false);
            lbl.setPreferredSize(new Dimension(250, 65));
            lbl.setOpaque(true);
            lbl.setBackground(clLeftItem);
            lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pnMenuItems.add(lbl);
        }

        // Set Trang chủ as default selected and visible
        lblTrangChu.setBackground(clLeftItemSelected);
        lblTrangChu.setVisible(true);
        lblBanHang.setVisible(true);
        lblKhuyenMai.setVisible(true);

        pnMenuLeft.add(pnMenuItems, BorderLayout.CENTER);

        // ========== PANEL THỜI GIAN VÀ ĐĂNG XUẤT Ở CUỐI SIDEBAR ==========
        JPanel pnBottomInfo = new JPanel();
        pnBottomInfo.setPreferredSize(new Dimension(250, 100));
        pnBottomInfo.setBackground(new Color(45, 52, 65));
        pnBottomInfo.setLayout(new BorderLayout());
        pnBottomInfo.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(51, 202, 187)));

        createSidebarBottomPanel(pnBottomInfo);
        pnMenuLeft.add(pnBottomInfo, BorderLayout.SOUTH);

        pnMain.add(pnMenuLeft, BorderLayout.WEST);

        /*
        ============================================================
                                CARD PANEL           
        ============================================================
         */
        pnCard = new JPanel(cardMenuLeftGroup);

        pnTrangChu = new JPanel();
        pnBanHang = new JPanel();
        pnKhuyenMai = new JPanel();
        pnNhapHang = new JPanel();
        pnSanPham = new JPanel();
        pnNhanVien = new JPanel();
        pnKhachHang = new JPanel();
        pnThongKe = new JPanel();

        pnCard.add(pnTrangChu, "0");
        pnCard.add(pnBanHang, "1");
        pnCard.add(pnKhuyenMai, "2");
        pnCard.add(pnNhapHang, "3");
        pnCard.add(pnSanPham, "4");
        pnCard.add(pnNhanVien, "5");
        pnCard.add(pnKhachHang, "6");
        pnCard.add(pnThongKe, "7");

        //==========ADD PANEL TRANG CHỦ (Always visible)==========
        trangChuPanel = new PnQuanLyTrangChuGUI(); // You need to create this class
        pnTrangChu.setLayout(new BorderLayout());
        pnTrangChu.add(trangChuPanel, BorderLayout.CENTER);

        //==========ADD PANEL BÁN HÀNG + KHUYẾN MÃI (Ko phân quyền)==========
        banHangPanel = new PnQuanLyBanHangGUI();
        pnBanHang.setLayout(new BorderLayout());
        pnBanHang.add(banHangPanel, BorderLayout.CENTER);

        khuyenMaiPanel = new PnQuanLyKhuyenMaiGUI();
        pnKhuyenMai.setLayout(new BorderLayout());
        pnKhuyenMai.add(khuyenMaiPanel, BorderLayout.CENTER);

        //======XỬ LÝ PHÂN QUYỀN=======
        PhanQuyen quyen = PhanQuyenBUS.quyenTK;

        if (quyen.getNhapHang() == 1) {
            nhapHangPanel = new PnQuanLyNhapHangGUI();
            pnNhapHang.setLayout(new BorderLayout());
            pnNhapHang.add(nhapHangPanel, BorderLayout.CENTER);
            lblNhapHang.setVisible(true);
        }

        if (quyen.getQlSanPham() == 1) {
            sanPhamPanel = new PnQuanLySanPhamGUI();
            pnSanPham.setLayout(new BorderLayout());
            pnSanPham.add(sanPhamPanel, BorderLayout.CENTER);
            lblSanPham.setVisible(true);
        }

        if (quyen.getQlNhanVien() == 1) {
            nhanVienPanel = new PnQuanLyNhanVienGUI();
            pnNhanVien.setLayout(new BorderLayout());
            pnNhanVien.add(nhanVienPanel, BorderLayout.CENTER);
            lblNhanVien.setVisible(true);
        }

        if (quyen.getQlKhachHang() == 1) {
            khachHangPanel = new PnQuanLyKhachHangGUI();
            pnKhachHang.setLayout(new BorderLayout());
            pnKhachHang.add(khachHangPanel, BorderLayout.CENTER);
            lblKhachHang.setVisible(true);
        }

        if (quyen.getThongKe() == 1) {
            thongKePanel = new PnQuanLyThongKeGUI();
            pnThongKe.setLayout(new BorderLayout());
            pnThongKe.add(thongKePanel, BorderLayout.CENTER);
            lblThongKe.setVisible(true);
        }
        
        pnMain.add(pnCard, BorderLayout.CENTER);
        
        con.add(pnMain);
    }

    // Phương thức tạo panel thông tin ở đầu sidebar
    private void createSidebarInfoPanel(JPanel infoPanel) {
        PhanQuyen quyen = PhanQuyenBUS.quyenTK;

        JPanel mainInfo = new JPanel();
        mainInfo.setOpaque(false);
        mainInfo.setLayout(new BorderLayout());
        mainInfo.setBorder(new EmptyBorder(10, 15, 10, 15));

       JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
       avatarPanel.setOpaque(false);
       avatarPanel.setPreferredSize(new Dimension(100, 80)); // Tăng kích thước panel

JLabel lblAvatar = new JLabel();
if (nhanVien.getGioiTinh().equals("Nam")) {
    FlatSVGIcon icon = new FlatSVGIcon("./icon/man_50px.svg");
    lblAvatar.setIcon(icon.derive(90, 90));
} else {
    // Tạo icon với kích thước lớn hơn (80x80 pixels)
    FlatSVGIcon icon = new FlatSVGIcon("./icon/women_50px.svg");
    lblAvatar.setIcon(icon.derive(90, 90));
}
avatarPanel.add(lblAvatar);

        // Panel thông tin text
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // Tên nhân viên
        lblUsername = new JLabel(nhanVien.getHo() + " " + nhanVien.getTen());
        lblUsername.setFont(new Font("Arial", Font.BOLD, 18)); // to hơn
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);

// Label chức vụ
        lblTenNhomQuyen = new JLabel(quyen.getQuyen());
        lblTenNhomQuyen.setFont(new Font("Arial", Font.PLAIN, 14)); // to hơn
        lblTenNhomQuyen.setForeground(new Color(51, 202, 187));
            lblTenNhomQuyen.setAlignmentX(Component.CENTER_ALIGNMENT);
            
        textPanel.add(Box.createVerticalStrut(6)); 
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(lblUsername);
        textPanel.add(Box.createVerticalStrut(3)); 
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(lblTenNhomQuyen);

        mainInfo.add(avatarPanel, BorderLayout.NORTH);
        mainInfo.add(textPanel, BorderLayout.CENTER);

        infoPanel.add(mainInfo, BorderLayout.CENTER);

        // Sự kiện click vào avatar
        lblAvatar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                  new DlgDoiMatKhau(taikhoan).setVisible(true);
            }
            
            @Override
            public void mouseEntered(MouseEvent evt) {
                
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
               
            }
        });
    }

    // Phương thức tạo panel thời gian và đăng xuất ở cuối sidebar
    private void createSidebarBottomPanel(JPanel bottomPanel) {
    JPanel mainBottom = new JPanel();
    mainBottom.setOpaque(false);
    mainBottom.setLayout(new BorderLayout());
    mainBottom.setBorder(new EmptyBorder(8, 15, 8, 15));
    
    // Panel thời gian với layout cải tiến
    JPanel timePanel = new JPanel();
    timePanel.setOpaque(false);
    timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
    
    // Panel cho giờ và ngày cạnh nhau
    JPanel dateTimeRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
    dateTimeRow.setOpaque(false);
    
    lblTime = new JLabel();
    lblTime.setFont(new Font("Arial", Font.BOLD, 25));
    lblTime.setForeground(Color.WHITE);
    
    dateTimeRow.add(Box.createVerticalStrut(6));
    dateTimeRow.add(lblTime);
    
    timePanel.add(dateTimeRow);
    timePanel.add(Box.createVerticalStrut(3));
    
    // Nút đăng xuất với icon và cỡ chữ lớn hơn
    JButton btnLogout = new JButton(" Đăng xuất");
    
    try {
        // Nếu có FlatSVGIcon
        FlatSVGIcon logoutIcon = new FlatSVGIcon("./icon/log_out.svg");
        btnLogout.setIcon(logoutIcon.derive(16, 16));
    } catch (Exception e) {
        // Fallback: sử dụng Unicode icon
        btnLogout.setText("Đăng xuất");
    }
    
    btnLogout.setFont(new Font("Arial", Font.BOLD, 12)); // Tăng từ 10 lên 12
    btnLogout.setBackground(new Color(220, 53, 69));
    btnLogout.setForeground(new Color(0, 102, 204)); // xanh nước biển tươi
    btnLogout.setFocusPainted(false);
    btnLogout.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12)); // Padding lớn hơn
    btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btnLogout.setPreferredSize(new Dimension(130, 30)); // Kích thước lớn hơn
    btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    // Hiệu ứng hover cho nút đăng xuất
    btnLogout.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            btnLogout.setBackground(new Color(200, 35, 51));
            btnLogout.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1),
                BorderFactory.createEmptyBorder(5, 11, 5, 11)
            ));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            btnLogout.setBackground(new Color(220, 53, 69));
            btnLogout.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
           
         DangNhapGUI login;
            try {
                dispose(); // Dispose current frame
                login = new DangNhapGUI();
                 login.setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(MainQuanLyGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
     

        }
    });
    
    // Panel cho nút đăng xuất
    JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    logoutPanel.setOpaque(false);
    logoutPanel.add(btnLogout);
    
    // Thêm khoảng cách giữa thời gian và nút đăng xuất
    mainBottom.add(timePanel, BorderLayout.CENTER);
    mainBottom.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
    
    // Panel tổng hợp
    JPanel finalPanel = new JPanel(new BorderLayout());
    finalPanel.setOpaque(false);
    finalPanel.add(timePanel, BorderLayout.CENTER);
    finalPanel.add(logoutPanel, BorderLayout.SOUTH);
    
    mainBottom.removeAll();
    mainBottom.add(finalPanel, BorderLayout.CENTER);
    bottomPanel.add(mainBottom, BorderLayout.CENTER);
}

// Phương thức cập nhật thời gian cải tiến  

    // Khởi động đồng hồ
        private void startClock() {
        clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
                // Cập nhật ngày nếu cần
            }
        });
        clockTimer.start();
        updateTime(); // Cập nhật lần đầu
    }


     private void updateTime() {
        if (lblTime != null) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(now.format(timeFormatter));
        }
    }

   

    int xMouse, yMouse;

    private void addEvents() {
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                moverFrame(e.getXOnScreen(), e.getYOnScreen());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY();
            }
        });

        btnMinimize.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                thuNhoFrame();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnMinimize.setIcon(new ImageIcon("image/ManagerUI/btn-minimize--hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnMinimize.setIcon(new ImageIcon("image/ManagerUI/btn-minimize.png"));
            }
        });

        btnClose.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                thoatChuongTrinh();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnClose.setIcon(new ImageIcon("image/ManagerUI/btn-close--hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnClose.setIcon(new ImageIcon("image/ManagerUI/btn-close.png"));
            }
        });

        for (JLabel lbl : listMenuLeft) {
            lbl.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (JLabel lblDisable : listMenuLeft) {
                        lblDisable.setBackground(clLeftItem);
                    }
                    lbl.setBackground(clLeftItemSelected);

                    // Xử lý lật trang theo menu - FIXED
                    String cardName = "";
                    if (lbl == lblTrangChu) {
                        cardName = "0";
                    } else if (lbl == lblBanHang) {
                        cardName = "1";
                    } else if (lbl == lblKhuyenMai) {
                        cardName = "2";
                    } else if (lbl == lblNhapHang) {
                        cardName = "3";
                    } else if (lbl == lblSanPham) {
                        cardName = "4";
                    } else if (lbl == lblNhanVien) {
                        cardName = "5";
                    } else if (lbl == lblKhachHang) {
                        cardName = "6";
                    } else if (lbl == lblThongKe) {
                        cardName = "7";
                    }
                    cardMenuLeftGroup.show(pnCard, cardName);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (lbl.getBackground().equals(clLeftItem)) {
                        lbl.setBackground(clLeftItemHover);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (lbl.getBackground().equals(clLeftItemHover)) {
                        lbl.setBackground(clLeftItem);
                    }
                }
            });
        }
    }

    private void moverFrame(int x, int y) {
        this.setLocation(x - xMouse, y - yMouse);
    }

    private void thuNhoFrame() {
        this.setState(Frame.ICONIFIED);
    }

    private void thoatChuongTrinh() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn thoát khỏi chương trình?",
            "Xác nhận thoát",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            if (clockTimer != null) {
                clockTimer.stop();
            }
             banHangPanel.xuLyThoat();
             System.exit(0);
            Main.Main.changLNF("Nimbus");
            
        }
    }
}