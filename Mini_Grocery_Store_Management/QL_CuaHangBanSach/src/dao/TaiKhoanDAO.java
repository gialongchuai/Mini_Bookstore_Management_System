/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.SQLServerProvider.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MSII
 */
public class TaiKhoanDAO {

    // Phương thức để lấy thông tin của tất cả người dùng và trả về một ArrayList
    public List<Object[]> getAllUsers() {
        List<Object[]> userList = new ArrayList<>();
        Connection connection = getConnection(); // Bạn cần tự định nghĩa phương thức này để kết nối vào cơ sở dữ liệu

        if (connection != null) {
            String query = "SELECT MaNguoiDung, TenNguoiDung, Email, SoDienThoai, DiaChi, TenDangNhap, MatKhau, VaiTro FROM NguoiDung";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Object[] row = new Object[8];
                    row[0] = rs.getInt("MaNguoiDung");
                    row[1] = rs.getString("TenNguoiDung");
                    row[2] = rs.getString("Email");
                    row[3] = rs.getString("SoDienThoai");
                    row[4] = rs.getString("DiaChi");
                    row[5] = rs.getString("TenDangNhap");
                    row[6] = rs.getString("MatKhau");
                    row[7] = rs.getString("VaiTro");
                    userList.add(row);
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }

    // Phương thức kiểm tra mã người dùng tồn tại
    public static boolean checkMaNguoiDungExists(Connection connection, String maNguoiDung) throws SQLException {
        String queryCheckMa = "SELECT * FROM NguoiDung WHERE MaNguoiDung = ?";
        PreparedStatement psCheckMa = connection.prepareStatement(queryCheckMa);
        psCheckMa.setString(1, maNguoiDung);
        ResultSet rsCheckMa = psCheckMa.executeQuery();
        return rsCheckMa.next();
    }

    // Phương thức kiểm tra email tồn tại
    public static boolean checkEmailExists(Connection connection, String email) throws SQLException {
        String queryCheckEmail = "SELECT * FROM NguoiDung WHERE Email = ?";
        PreparedStatement psCheckEmail = connection.prepareStatement(queryCheckEmail);
        psCheckEmail.setString(1, email);
        ResultSet rsCheckEmail = psCheckEmail.executeQuery();
        return rsCheckEmail.next();
    }

    // Phương thức kiểm tra số điện thoại tồn tại
    public static boolean checkSoDienThoaiExists(Connection connection, String soDienThoai) throws SQLException {
        String queryCheckSDT = "SELECT * FROM NguoiDung WHERE SoDienThoai = ?";
        PreparedStatement psCheckSDT = connection.prepareStatement(queryCheckSDT);
        psCheckSDT.setString(1, soDienThoai);
        ResultSet rsCheckSDT = psCheckSDT.executeQuery();
        return rsCheckSDT.next();
    }

    // Phương thức kiểm tra tên đăng nhập tồn tại
    public static boolean checkTenDangNhapExists(Connection connection, String tenDangNhap) throws SQLException {
        String queryCheckTenDN = "SELECT * FROM NguoiDung WHERE TenDangNhap = ?";
        PreparedStatement psCheckTenDN = connection.prepareStatement(queryCheckTenDN);
        psCheckTenDN.setString(1, tenDangNhap);
        ResultSet rsCheckTenDN = psCheckTenDN.executeQuery();
        return rsCheckTenDN.next();
    }

    // Phương thức thêm người dùng vào cơ sở dữ liệu
    public static boolean insertNguoiDung(Connection connection, String maNguoiDung, String tenNguoiDung, String email, String soDienThoai, String diaChi, String tenDangNhap, String matKhau, String vaiTro) throws SQLException {
        String queryInsert = "INSERT INTO NguoiDung (MaNguoiDung, TenNguoiDung, Email, SoDienThoai, DiaChi, TenDangNhap, MatKhau, VaiTro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement psInsert = connection.prepareStatement(queryInsert);
        psInsert.setString(1, maNguoiDung);
        psInsert.setString(2, tenNguoiDung);
        psInsert.setString(3, email);
        psInsert.setString(4, soDienThoai);
        psInsert.setString(5, diaChi);
        psInsert.setString(6, tenDangNhap);
        psInsert.setString(7, matKhau);
        psInsert.setString(8, vaiTro);
        int rowsAffected = psInsert.executeUpdate();
        return rowsAffected > 0;
    }
}
