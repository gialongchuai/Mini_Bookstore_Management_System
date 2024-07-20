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
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import pojo.LayHoaDon;

/**
 *
 * @author MSII
 */
public class HoaDonDAO {

    public List<LayHoaDon> getAllLayHoaDon() {
        List<LayHoaDon> layHoaDonList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM HoaDon";
            PreparedStatement statement = SQLServerProvider.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                LayHoaDon layHoaDon = new LayHoaDon(
                        rs.getInt("MaHoaDon"),
                        rs.getTimestamp("NgayMua"),
                        rs.getDouble("TongTien"),
                        rs.getInt("MaNguoiDung")
                );
                layHoaDonList.add(layHoaDon);
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return layHoaDonList;
    }

    public void loadChiTietHoaDonData(DefaultTableModel model) {
        try {
            String sql = "SELECT * FROM ChiTietHoaDon";
            PreparedStatement statement = SQLServerProvider.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            model.setRowCount(0); // Xóa tất cả các dòng cũ

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("MaHoaDon"),
                    rs.getInt("MaSach"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("GiaBan")
                };
                model.addRow(row);
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
