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
import javax.swing.JOptionPane;
import pojo.Sach;
import pojo.SachDB;

/**
 *
 * @author MSII
 */
public class SachDAO {

    // Phương thức để lấy danh sách Tên Danh mục từ cơ sở dữ liệu
    public List<String> layDanhSachTenDanhMuc() throws SQLException {
        List<String> danhSachTenDanhMuc = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = SQLServerProvider.getConnection();
            String query = "SELECT TenDanhMuc FROM DanhMucSach";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                String tenDanhMuc = rs.getString("TenDanhMuc");
                danhSachTenDanhMuc.add(tenDanhMuc);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return danhSachTenDanhMuc;
    }

    // Phương thức để load dữ liệu sách từ cơ sở dữ liệu vào danh sách
    public List<SachDB> getAllSach() {
        List<SachDB> sachList = new ArrayList<>();
        try {
            // Kết nối đến cơ sở dữ liệu
            Connection conn = SQLServerProvider.getConnection();

            // Thực hiện truy vấn để lấy dữ liệu từ bảng Sach
            String query = "SELECT * FROM Sach";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            // Đọc dữ liệu từ ResultSet và thêm vào danh sách sách
            while (rs.next()) {
                SachDB sach = new SachDB();
                sach.setMaSach(rs.getInt("MaSach"));
                sach.setTenSach(rs.getString("TenSach"));
                sach.setMoTaSach(rs.getString("MoTaSach"));
                sach.setTacGia(rs.getString("TacGia"));
                sach.setGia(rs.getDouble("Gia"));
                sach.setSoLuong(rs.getInt("SoLuong"));
                sach.setMaDanhMuc(rs.getInt("MaDanhMuc"));
                sachList.add(sach);
            }

            // Đóng kết nối và tài nguyên
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sachList;
    }

    public boolean kiemTraDuLieu(String maSach, String tenSach, String moTaSach, String tacGia, String gia, String soLuong) {
        // Phương thức kiểm tra dữ liệu nhập vào
        if (maSach.isEmpty() || tenSach.isEmpty() || moTaSach.isEmpty() || tacGia.isEmpty() || gia.isEmpty() || soLuong.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean kiemTraTrungMaSach(String maSach) {
        // Phương thức kiểm tra trùng lặp mã sách
        String query = "SELECT MaSach FROM Sach WHERE MaSach = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maSach);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Nếu có kết quả trả về, mã sách đã tồn tại
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra trùng mã sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean kiemTraTrungTenSach(String tenSach) {
        // Phương thức kiểm tra trùng lặp tên sách
        String query = "SELECT TenSach FROM Sach WHERE TenSach = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, tenSach);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Nếu có kết quả trả về, tên sách đã tồn tại
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra trùng tên sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean themSach(String maSach, String tenSach, String moTaSach, String tacGia, String gia, String soLuong, String tenDanhMuc) {
        if (!kiemTraDuLieu(maSach, tenSach, moTaSach, tacGia, gia, soLuong)) {
            return false; // Không thêm nếu thông tin không đầy đủ
        }

        if (kiemTraTrungMaSach(maSach)) {
            JOptionPane.showMessageDialog(null, "Mã sách đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (kiemTraTrungTenSach(tenSach)) {
            JOptionPane.showMessageDialog(null, "Tên sách đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int giaInt = Integer.parseInt(gia);
            int soLuongInt = Integer.parseInt(soLuong);
            if (giaInt <= 0 || soLuongInt <= 0) {
                JOptionPane.showMessageDialog(null, "Hãy kiểm tra lại giá và số lượng của sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Hãy kiểm tra lại giá và số lượng của sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String maDanhMuc = layMaDanhMucTheoTen(tenDanhMuc);
        if (maDanhMuc == null) {
            JOptionPane.showMessageDialog(null, "Tên danh mục không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String query = "INSERT INTO Sach (MaSach, TenSach, MoTaSach, TacGia, Gia, SoLuong, MaDanhMuc) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maSach);
            pstmt.setString(2, tenSach);
            pstmt.setString(3, moTaSach);
            pstmt.setString(4, tacGia);
            pstmt.setString(5, gia);
            pstmt.setString(6, soLuong);
            pstmt.setString(7, maDanhMuc);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Thêm sách thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private String layMaDanhMucTheoTen(String tenDanhMuc) {
        // Phương thức lấy mã danh mục từ tên danh mục
        String maDanhMuc = null;
        String query = "SELECT MaDanhMuc FROM DanhMucSach WHERE TenDanhMuc = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, tenDanhMuc);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    maDanhMuc = rs.getString("MaDanhMuc");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã danh mục: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return maDanhMuc;
    }

    public boolean deleteSach(String maSach) {
        try {
            String sql = "DELETE FROM Sach WHERE MaSach = ?";
            Connection conn = SQLServerProvider.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, maSach);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String layTenDanhMucTuMaSach(String maSach) throws SQLException {
        Connection conn = SQLServerProvider.getConnection();

        String query = "SELECT d.TenDanhMuc FROM DanhMucSach d INNER JOIN Sach s ON d.MaDanhMuc = s.MaDanhMuc WHERE s.MaSach = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, maSach);
        ResultSet rs = pst.executeQuery();

        String tenDanhMuc = null;
        if (rs.next()) {
            tenDanhMuc = rs.getString("TenDanhMuc");
        }

        rs.close();
        pst.close();
        conn.close();

        return tenDanhMuc;
    }
}
