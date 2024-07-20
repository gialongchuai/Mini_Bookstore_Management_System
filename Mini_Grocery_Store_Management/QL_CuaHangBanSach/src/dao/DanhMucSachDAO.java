/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MSII
 */
public class DanhMucSachDAO {

    public List<Object[]> layDanhSachDanhMuc() {
        List<Object[]> danhSach = new ArrayList<>();
        Connection connection = SQLServerProvider.getConnection();

        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM DanhMucSach");

                while (rs.next()) {
                    Object[] row = new Object[]{
                        rs.getInt("MaDanhMuc"),
                        rs.getString("TenDanhMuc"),
                        rs.getString("MoTaDanhMuc")
                    };
                    danhSach.add(row);
                }

                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Lỗi khi thực hiện truy vấn: " + e.getMessage());
            }
        }
        return danhSach;
    }

    // Hàm kiểm tra xem mã danh mục đã tồn tại chưa
    public boolean maDanhMucTonTai(String maDanhMuc) {
        try {
            String query = "SELECT * FROM DanhMucSach WHERE MaDanhMuc = ?";
            PreparedStatement preparedStatement = SQLServerProvider.getConnection().prepareStatement(query);
            preparedStatement.setString(1, maDanhMuc);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Hàm kiểm tra xem tên danh mục đã tồn tại chưa
    public boolean tenDanhMucTonTai(String tenDanhMuc) {
        try {
            String query = "SELECT * FROM DanhMucSach WHERE TenDanhMuc = ?";
            PreparedStatement preparedStatement = SQLServerProvider.getConnection().prepareStatement(query);
            preparedStatement.setString(1, tenDanhMuc);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Hàm thêm dữ liệu vào cơ sở dữ liệu
    public boolean themDanhMuc(String maDanhMuc, String tenDanhMuc, String moTaDanhMuc) {
        try {
            String query = "INSERT INTO DanhMucSach (MaDanhMuc, TenDanhMuc, MoTaDanhMuc) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = SQLServerProvider.getConnection().prepareStatement(query);
            preparedStatement.setString(1, maDanhMuc);
            preparedStatement.setString(2, tenDanhMuc);
            preparedStatement.setString(3, moTaDanhMuc);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteDanhMuc(String maDanhMuc) {
        Connection connection = SQLServerProvider.getConnection();
        try {
            String sql = "DELETE FROM DanhMucSach WHERE MaDanhMuc = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, maDanhMuc);

            int rowsDeleted = statement.executeUpdate();
            statement.close();
            connection.close();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDanhMucSach(int maDanhMuc, String tenDanhMuc, String moTaDanhMuc) {
        Connection connection = SQLServerProvider.getConnection();
        try {
            String sql = "UPDATE DanhMucSach SET TenDanhMuc = ?, MoTaDanhMuc = ? WHERE MaDanhMuc = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tenDanhMuc);
            statement.setString(2, moTaDanhMuc);
            statement.setInt(3, maDanhMuc);

            int rowsUpdated = statement.executeUpdate();
            statement.close();
            connection.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Object[]> timKiem(String keyword) throws SQLException {
        List<Object[]> results = new ArrayList<>();

        String sql = "SELECT * FROM DanhMucSach WHERE TenDanhMuc LIKE ? OR MoTaDanhMuc LIKE ?";
        try (Connection connection = SQLServerProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            // Thiết lập tham số cho câu truy vấn
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            // Thực thi câu truy vấn
            ResultSet resultSet = statement.executeQuery();
            // Duyệt kết quả và thêm vào danh sách kết quả
            while (resultSet.next()) {
                int maDanhMuc = resultSet.getInt("MaDanhMuc");
                String tenDanhMuc = resultSet.getString("TenDanhMuc");
                String moTaDanhMuc = resultSet.getString("MoTaDanhMuc");
                results.add(new Object[]{maDanhMuc, tenDanhMuc, moTaDanhMuc});
            }
        } catch (SQLException e) {
            throw e;
        }

        return results;
    }
    
    public static int tuDongMaDanhMuc() throws SQLException {
        int newNumber = 1;
        List<Integer> usedNumbers = new ArrayList<>();

        String query = "SELECT MaDanhMuc FROM DanhMucSach";
        try (Connection conn = SQLServerProvider.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                usedNumbers.add(rs.getInt("MaDanhMuc"));
            }

            while (true) {
                if (!usedNumbers.contains(newNumber)) {
                    break;
                }
                newNumber++;
            }
        } catch (SQLException e) {
            throw e;
        }

        return newNumber;
    }
}
