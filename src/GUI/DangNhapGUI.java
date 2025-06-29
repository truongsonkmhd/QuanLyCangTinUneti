package GUI;

import BUS.DangNhapBUS;
import DTO.TaiKhoan;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DangNhapGUI extends JFrame implements ActionListener {

    public DangNhapGUI() throws Exception {
        initComponents();
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        
        xuLyTaiKhoanDaGhiNho();
    }
    
      private void xuLyTaiKhoanDaGhiNho() {
        DangNhapBUS dangNhapBUS = new DangNhapBUS();
        String line = dangNhapBUS.getTaiKhoanGhiNho();
        try {
            String[] arr = line.split(" | ");
            chkRemember.setSelected(true);
            tfName.setText(arr[0]);
            pfPass.setText(arr[2]);
            tfName.requestFocus();
        } catch (Exception e) {
            tfName.setText("");
            pfPass.setText("");
            tfName.requestFocus();
        }
    }
      
       private void xuLyDangNhap() {
    DangNhapBUS dangNhapBUS = new DangNhapBUS();
    TaiKhoan tk = dangNhapBUS.getTaiKhoanDangNhap(tfName.getText(), pfPass.getText(), chkRemember.isSelected());
    if (tk != null) {
        MainQuanLyGUI gui = new MainQuanLyGUI(tk);
        gui.showWindow();
        this.dispose();
    } 
       }

        
    public void showWindow() {
        Image icon = Toolkit.getDefaultToolkit().getImage("image/ManagerUI/icon-app.png");
        this.setIconImage(icon);
        this.setVisible(true);
    }


    int x_Mouse, y_Mouse;
    
    private void formMousePressed(java.awt.event.MouseEvent evt) {                                  
        x_Mouse = evt.getX();
        y_Mouse = evt.getY();
    }                                 

    private void formMouseDragged(java.awt.event.MouseEvent evt) {                                  
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        setLocation(x - x_Mouse, y - y_Mouse);
    }         
    
    public void initComponents() throws Exception {
        
        Arial = new Font("Arial", Font.PLAIN, 15);
        ArialBold = new Font("Arial", Font.BOLD, 18);
        ArialTitle = new Font("Arial", Font.BOLD, 20);
        
        // Màu sắc hài hòa hơn
        colorPanel_R = new Color(255, 255, 255); // Màu trắng
        colorAccent = new Color(52, 152, 219); // Xanh dương
        colorAccentHover = new Color(41, 128, 185);
        colorAccentPressed = new Color(31, 120, 180);
      
       try {
    ImageIcon rawIcon = new ImageIcon("image/ManagerUI/avatar.png");
    Image scaled = rawIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    iconLoginLarge = new ImageIcon(scaled);
} catch (Exception e) {
    iconLoginLarge = null;
}

        // Khởi tạo components
        pLoginForm = new JPanel();
        btnExit = new JButton();
        try {
    ImageIcon icon = new ImageIcon("image/delete-icon.png"); // Đường dẫn đến ảnh icon
    Image img = icon.getImage().getScaledInstance(20, 18, Image.SCALE_SMOOTH);
    btnExit.setIcon(new ImageIcon(img));
} catch (Exception e) {
    btnExit.setText("×"); // fallback nếu icon không load được
    btnExit.setFont(new Font("Arial", Font.BOLD, 18));
}

      
        
        // Custom input components
        lblLoginTitle = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG", SwingConstants.CENTER);
        lblLoginIcon = new JLabel(iconLoginLarge, SwingConstants.CENTER);
        lblName = new JLabel("Tên đăng nhập");
        lblPass = new JLabel("Mật khẩu");
        
        tfName = new JTextField();
        pfPass = new JPasswordField();
        
        // Checkbox ghi nhớ đăng nhập
        chkRemember = new JCheckBox("Ghi nhớ đăng nhập");
        
        // Tạo label image với placeholder nếu không tìm thấy file
        try {
    ImageIcon originalIcon = new ImageIcon("image/Poster/Poster_1.png");
    Image img = originalIcon.getImage().getScaledInstance(500, 650, Image.SCALE_SMOOTH);
    lblImage = new JLabel(new ImageIcon(img));
    lblImage.setLayout(null); // Cần thiết để setBounds cho các component con
    lblImage.setBounds(0, 0, 500, 650); // Đặt vị trí và kích thước cụ thể
} catch (Exception e) {
    lblImage = new JLabel("IMAGE SLIDESHOW", SwingConstants.CENTER);
    lblImage.setFont(new Font("Arial", Font.BOLD, 24));
    lblImage.setForeground(Color.WHITE);
    lblImage.setBackground(new Color(52, 152, 219));
    lblImage.setOpaque(true);
    lblImage.setLayout(null);
    lblImage.setBounds(0, 0, 500, 650);
}

        pRadioButtons = createPanel_RadioButton(3);
pRadioButtons.setBounds(190, 600, 120, 30); // Điều chỉnh vị trí phù hợp trên ảnh
lblImage.add(pRadioButtons); // Thêm vào lblImage thay vì frame

        lblLogin = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        
        // Label quên mật khẩu
        lblForgotPassword = new JLabel("Quên mật khẩu?", SwingConstants.CENTER);

        // JFrame setup
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLayout(null); // Sử dụng absolute layout
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        
        // Setup left panel for image
        setupLeftPanel();
        
        // Setup right panel for login form
        setupRightPanel();
        
        // Add components to frame
        this.add(lblImage);
        this.add(btnExit);
        this.add(pLoginForm);

        // Timer cho slideshow
        time = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int i = 0; i < radiobuttons.length; i++) {
                    if (radiobuttons[i].isSelected()) {
                        if (i == radiobuttons.length - 1)
                            i = -1;
                        setButtonImageIcon(i + 1);
                        radiobuttons[i + 1].setSelected(true);
                        break;
                    }
                }
            }
        });
        time.start();
    }
    
    private void setupLeftPanel() {
        // Poster fill toàn bộ bên trái
        lblImage.setBounds(0, 0, 500, 650);
        lblImage.setBorder(null); // Bỏ border để fill toàn màn hình
        
        // Radio buttons overlay trên poster
        pRadioButtons.setBounds(150, 580, 200, 35);
        pRadioButtons.setOpaque(false); // Trong suốt để thấy poster phía sau
    }
    
    private void setupRightPanel() {
        // Exit button
        btnExit.setBounds(970, 5, 25, 25);
        btnExit.setFont(new Font("Arial", Font.BOLD, 18));
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnExit.setBackground(new Color(255, 100, 100));
                btnExit.setForeground(Color.WHITE);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnExit.setBackground(Color.WHITE);
                btnExit.setForeground(new Color(100, 100, 100));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                actionBtnClose();
            }
        });

        // Logo - giảm kích thước và đặt ở góc

        // DangNhapGUI Form Panel - chiếm toàn bộ không gian bên phải
        pLoginForm.setLayout(null);
        pLoginForm.setBounds(500, 0, 500, 650);
        pLoginForm.setBackground(Color.WHITE);
        pLoginForm.setBorder(null);

        setupLoginFormComponents();
    }
    
    private void setupLoginFormComponents() {
        // Components trong login form
        pLoginForm.add(lblLoginTitle);
        pLoginForm.add(lblLoginIcon);
        pLoginForm.add(lblName);
        pLoginForm.add(tfName);
        pLoginForm.add(lblPass);
        pLoginForm.add(pfPass);
        pLoginForm.add(chkRemember);
        pLoginForm.add(lblLogin);
        pLoginForm.add(lblForgotPassword);

        // DangNhapGUI title - căn giữa màn hình phải
        lblLoginTitle.setBounds(50, 120, 400, 40);
        lblLoginTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblLoginTitle.setForeground(new Color(44, 62, 80));

        // Icon lớn căn giữa và đẹp hơn
        lblLoginIcon.setBounds(175, 170, 150, 150);

        // Username label và textfield
        lblName.setBounds(75, 340, 150, 30);
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        lblName.setForeground(new Color(52, 73, 94));

        tfName.setBounds(75, 370, 350, 45);
        tfName.setFont(new Font("Arial", Font.PLAIN, 16));
        tfName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        tfName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                tfName.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(colorAccent, 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                tfName.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });
        tfName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                    pfPass.requestFocus();
                }
            }
        });

        // Password label và passwordfield
        lblPass.setBounds(75, 430, 150, 30);
        lblPass.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPass.setForeground(new Color(52, 73, 94));

        pfPass.setBounds(75, 460, 350, 45);
        pfPass.setFont(new Font("Arial", Font.PLAIN, 16));
        pfPass.setEchoChar('●');
        pfPass.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        pfPass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pfPass.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(colorAccent, 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                pfPass.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });
        pfPass.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent key) {
                if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                    xuLyDangNhap();
                }
            }
        });

        // Checkbox ghi nhớ
        chkRemember.setBounds(75, 520, 200, 35);
        chkRemember.setFont(new Font("Arial", Font.PLAIN, 14));
        chkRemember.setBackground(Color.WHITE);
        chkRemember.setForeground(new Color(52, 73, 94));
        chkRemember.setFocusPainted(false);

        // DangNhapGUI button với style đẹp hơn
        lblLogin.setBounds(75, 565, 350, 55);
        lblLogin.setFont(new Font("Arial", Font.BOLD, 18));
        lblLogin.setForeground(Color.WHITE);
        lblLogin.setBackground(colorAccent);
        lblLogin.setOpaque(true);
        lblLogin.setBorder(BorderFactory.createLineBorder(colorAccent, 0));
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                xuLyDangNhap();
            }

            @Override
            public void mousePressed(MouseEvent me) {
                lblLogin.setBackground(colorAccentPressed);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                lblLogin.setBackground(colorAccentHover);
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                lblLogin.setBackground(colorAccentHover);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                lblLogin.setBackground(colorAccent);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Forgot password label
        lblForgotPassword.setBounds(75, 625, 350, 25);
        lblForgotPassword.setFont(new Font("Arial", Font.ITALIC, 14));
        lblForgotPassword.setForeground(colorAccent);
        lblForgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                actionForgotPassword();
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                lblForgotPassword.setForeground(colorAccentHover);
                lblForgotPassword.setText("<html><u>Quên mật khẩu?</u></html>");
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                lblForgotPassword.setForeground(colorAccent);
                lblForgotPassword.setText("Quên mật khẩu?");
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
    
    public void setButtonImageIcon(int iNumb) {
        String url = "image/Poster/Poster_";
        int iNumber = iNumb + 1;
        url += String.valueOf(iNumber) + ".png";
        try {
            ImageIcon originalIcon = new ImageIcon(url);
            // Scale image để fill toàn màn hình bên trái
            Image img = originalIcon.getImage().getScaledInstance(500, 650, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // Nếu không tìm thấy ảnh, giữ nguyên
        }
    }

    private JPanel createPanel_RadioButton(int iNumbRadioBtn) {
        JPanel Panel = new JPanel();
        radiobuttons = new JRadioButton[3];
        buttongroup = new ButtonGroup();

        Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        Panel.setBackground(Color.WHITE);

        for (int i = 0; i < iNumbRadioBtn; i++) {
            radiobuttons[i] = new JRadioButton();
            radiobuttons[i].setName("radiobutton " + i);
            radiobuttons[i].addActionListener((ActionListener) this);
            radiobuttons[i].setPreferredSize(new Dimension(20, 20));
            radiobuttons[i].setFocusPainted(false);
            radiobuttons[i].setBackground(Color.WHITE);
            
            buttongroup.add(radiobuttons[i]);
            Panel.add(radiobuttons[i]);
        }
        radiobuttons[0].setSelected(true);
        
        return Panel;
    }

    private void actionForgotPassword() {
        JOptionPane.showMessageDialog(this, 
            "Vui lòng liên hệ quản trị viên để được hỗ trợ khôi phục mật khẩu.", 
            "Quên mật khẩu", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JRadioButton src = (JRadioButton) ae.getSource();
        String url = "image/Poster/Poster_";
        
        String[] srcName = src.getName().split(" ");
        int iNumber = Integer.parseInt(srcName[1]) + 1;
        
        url += String.valueOf(iNumber) + ".png";
        try {
            ImageIcon originalIcon = new ImageIcon(url);
            // Scale image để fill toàn màn hình bên trái
            Image img = originalIcon.getImage().getScaledInstance(500, 650, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            // Nếu không tìm thấy ảnh, giữ nguyên
        }
    }

    private void actionBtnClose() {
        if (time != null) {
            time.stop();
        }
        System.exit(0);
    }

   
    // Variables
    private Font Arial, ArialBold, ArialTitle;
    private Color colorPanel_R, colorAccent, colorAccentHover, colorAccentPressed;
    private JPanel pLeft, pRight, pRadioButtons, pLoginForm;
    private JButton btnExit; 
    private ImageIcon iconLoginLarge;
    private JLabel lblName, lblPass, lblLogin, lblLoginTitle, lblLoginIcon, lblForgotPassword;
    private JLabel lblImage;
    private JTextField tfName;
    private JPasswordField pfPass;
    private JCheckBox chkRemember;
    private JRadioButton[] radiobuttons;
    private ButtonGroup buttongroup;
    private Timer time;
}