
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.SQLServerProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DangNhapDAO {
    public static boolean dangNhap(String username, String password, String quyen) {
        Connection conn = SQLServerProvider.getConnection();
        if (conn != null) {
            try {
                String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ? AND VaiTro = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, quyen);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Đăng nhập thành công
                    rs.close();
                    pstmt.close();
                    conn.close();
                    return true;
                } else {
                    // Đăng nhập thất bại
                    rs.close();
                    pstmt.close();
                    conn.close();
                    return false;
                }
            } catch (SQLException e) {
                // Xử lý lỗi khi truy vấn cơ sở dữ liệu
                e.printStackTrace();
                return false;
            }
        } else {
            // Xử lý lỗi kết nối cơ sở dữ liệu
            return false;
        }
    }
}
