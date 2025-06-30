package DAO;

import DTO.TaiKhoan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangNhapDAO {

    public TaiKhoan getUserByUserEnter(String tenDangNhap) {
        try {
            String sql = "SELECT * FROM taikhoan WHERE TenDangNhap=? AND TrangThai=1";
            PreparedStatement pre = MyConnect.conn.prepareStatement(sql);
            pre.setString(1, tenDangNhap);
            ResultSet rs = pre.executeQuery();
            TaiKhoan tkLogin = null;
            if (rs.next()) {
                tkLogin = new TaiKhoan();
                tkLogin.setTenDangNhap(rs.getString("TenDangNhap"));
                tkLogin.setMatKhau(rs.getString("MatKhau"));
                tkLogin.setMaNhanVien(rs.getInt("MaNV"));
                tkLogin.setQuyen(rs.getString("Quyen"));
            }
            return tkLogin;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
