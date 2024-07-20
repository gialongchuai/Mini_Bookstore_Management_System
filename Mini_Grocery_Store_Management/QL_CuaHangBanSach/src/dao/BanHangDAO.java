/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static dao.SQLServerProvider.getConnection;
import gui.HoaDon;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import pojo.Sach;

/**
 *
 * @author MSII
 */
public class BanHangDAO {

    private JLabel lblNgayThangNam;

    public BanHangDAO() {

    }

    public BanHangDAO(JLabel lblNgayThangNam) {
        this.lblNgayThangNam = lblNgayThangNam;
    }

    private void capNhatThoiGian() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ngayThangNam = sdf.format(date);
        lblNgayThangNam.setText(ngayThangNam);
    }

    public void batDauCapNhatThoiGian() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    capNhatThoiGian();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    public List<String> getAllDanhMuc() {
        List<String> danhMucList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // Gọi phương thức kết nối cơ sở dữ liệu
            stmt = conn.createStatement();
            String sql = "SELECT TenDanhMuc FROM DanhMucSach"; // SQL truy vấn tên danh mục
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tenDanhMuc = rs.getString("TenDanhMuc");
                danhMucList.add(tenDanhMuc); // Thêm tên danh mục vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tải dữ liệu danh mục: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhMucList;
    }

    public List<Sach> getAllSach() {
        List<Sach> sachList = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT TenSach, MoTaSach, TacGia, Gia, SoLuong FROM Sach")) {

            while (rs.next()) {
                Sach sach = new Sach();
                sach.setTenSach(rs.getString("TenSach"));
                sach.setMoTaSach(rs.getString("MoTaSach"));
                sach.setTacGia(rs.getString("TacGia"));
                sach.setGia(rs.getInt("Gia"));
                sach.setSoLuong(rs.getInt("SoLuong"));
                sachList.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }
        return sachList;
    }

    // Phương thức lọc sách theo danh mục
    public List<Sach> getSachByDanhMuc(String tenDanhMuc) {
        List<Sach> sachList = new ArrayList<>();
        String sql = "SELECT Sach.TenSach, Sach.MoTaSach, Sach.TacGia, Sach.Gia, Sach.SoLuong "
                + "FROM Sach JOIN DanhMucSach ON Sach.MaDanhMuc = DanhMucSach.MaDanhMuc "
                + "WHERE DanhMucSach.TenDanhMuc = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tenDanhMuc);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sach sach = new Sach();
                sach.setTenSach(rs.getString("TenSach"));
                sach.setMoTaSach(rs.getString("MoTaSach"));
                sach.setTacGia(rs.getString("TacGia"));
                sach.setGia(rs.getInt("Gia"));
                sach.setSoLuong(rs.getInt("SoLuong"));
                sachList.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lọc sách theo danh mục: " + e.getMessage());
        }
        return sachList;
    }

    // Hàm lấy số lượng sách trong kho
    public static int laySoLuongTrongKho(String tenSach) {
        int soLuong = 0;
        String sql = "SELECT SoLuong FROM Sach WHERE TenSach = ?";
        try {
            Connection conn = getConnection(); // Bạn cần cung cấp phương thức getConnection() tương ứng
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tenSach);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi khi lấy số lượng sách trong kho: " + ex.getMessage());
        }
        return soLuong;
    }

    // Hàm kiểm tra xem một chuỗi có phải là số nguyên không
    public static boolean laSoNguyenDuong(String str) {
        try {
            int num = Integer.parseInt(str);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Hàm kiểm tra xem dữ liệu nhập vào là hợp lệ hay không
    public static boolean duLieuHopLe(String tenSach, String giaStr, String soLuongMuaStr) {
        if (tenSach.isEmpty() || giaStr.isEmpty() || soLuongMuaStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đủ thông tin.");
            return false;
        }

        if (!laSoNguyenDuong(soLuongMuaStr)) {
            JOptionPane.showMessageDialog(null, "Số lượng mua phải là một số nguyên dương.");
            return false;
        }

        int soLuongMua = Integer.parseInt(soLuongMuaStr);
        int soLuongTrongKho = laySoLuongTrongKho(tenSach);

        if (soLuongMua > soLuongTrongKho) {
            JOptionPane.showMessageDialog(null, "Số lượng sách không đủ trong kho.");
            return false;
        }

        return true;
    }

    // Hàm tính thành tiền
    public static double tinhThanhTien(int soLuongMua, double gia) {
        return soLuongMua * gia;
    }

    // Hàm kiểm tra xem sản phẩm đã tồn tại trong bảng hay chưa
    public static boolean kiemTraSanPhamTonTai(JTable table, String tenSach) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String tenSachTrongBang = (String) model.getValueAt(i, 1); // Lấy tên sách từ cột "tenSach"
            if (tenSachTrongBang.equals(tenSach)) {
                return true; // Sản phẩm đã tồn tại trong bảng
            }
        }
        return false; // Sản phẩm chưa tồn tại trong bảng
    }

    // Hàm tính tổng và hiển thị lên label
    public static void capNhatTongThanhTien(JTable table, JLabel label) {
        double tongThanhTien = 0;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            double thanhTien = (double) model.getValueAt(i, 4); // Lấy giá trị từ cột "thanhTien"
            tongThanhTien += thanhTien;
        }
        if (tongThanhTien > 0) {
            // Định dạng số nguyên có dấu chấm ngăn cách phần ngàn và thêm ký hiệu tiền tệ "₫"
            String tongThanhTienText = String.format("%,.0f", tongThanhTien) + " VNĐ.";
            label.setText("Tổng thành tiền: " + tongThanhTienText);
        } else {
            label.setText("Tổng thành tiền");
        }
    }

    private int maHoaDon;

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    
    
    public static int hoaDon = 0;
    
    // Phương thức tạo và lưu tệp PDF từ mô hình dữ liệu
    public static void generatePDF(TableModel modelHoaDon) {
        
        Document document = new Document();
        try {
            // Đường dẫn đến thư mục lưu trữ tệp PDF
            String folderPath = "C:\\Users\\MSII\\Desktop\\";
            // Tạo thư mục nếu nó không tồn tại
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Tạo tên file dựa trên thời gian hiện tại
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = "HoaDon_" + dateFormat.format(new Date()) + ".pdf";

            // Tạo một tệp PDF mới
            PdfWriter.getInstance(document, new FileOutputStream(folderPath + fileName));
            document.open();

            // Thêm tiêu đề "Hóa Đơn Thanh Toán" với khoảng trống ở dưới
            Font fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            Paragraph title = new Paragraph("HOA DON THANH TOAN", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(50f); // Khoảng cách dưới tiêu đề
            document.add(title);

            // Thêm mã hóa đơn vào tài liệu
            Paragraph maHoaDonParagraph = new Paragraph("Ma hoa don: " + hoaDon);
            maHoaDonParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(maHoaDonParagraph);

            // Thêm khoảng trống sau dòng "Ma hoa don"
            Paragraph emptySpaceAfterMaHoaDon = new Paragraph();
            emptySpaceAfterMaHoaDon.setSpacingAfter(30f); // Khoảng cách dưới "Ma hoa don"
            document.add(emptySpaceAfterMaHoaDon);

            // Tạo bảng PDF
            PdfPTable table = new PdfPTable(modelHoaDon.getColumnCount());

            // Thêm dữ liệu từ mô hình dữ liệu vào bảng PDF
            for (int i = 0; i < modelHoaDon.getColumnCount(); i++) {
                table.addCell(new Phrase(convertToUnaccentedVietnamese(modelHoaDon.getColumnName(i))));
            }
            for (int i = 0; i < modelHoaDon.getRowCount(); i++) {
                for (int j = 0; j < modelHoaDon.getColumnCount(); j++) {
                    table.addCell(new Phrase(convertToUnaccentedVietnamese(modelHoaDon.getValueAt(i, j).toString())));
                }
            }

            // Thêm bảng vào tài liệu
            document.add(table);

            // Thêm khoảng trống dưới bảng
            Paragraph emptySpace = new Paragraph();
            emptySpace.setSpacingAfter(50f); // Khoảng cách dưới bảng
            document.add(emptySpace);

            // Thêm dòng "Have a good day!" vào cuối file với khoảng trống ở trên
            Paragraph footer = new Paragraph("Have a good day!");
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(50f); // Khoảng cách trên dòng "Have a good day!"
            document.add(footer);

            // Đóng tài liệu
            document.close();

            JOptionPane.showMessageDialog(null, "Xuất hóa đơn thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức chuyển đổi chuỗi thành tiếng Việt không dấu
    private static String convertToUnaccentedVietnamese(String str) {
        str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        str = str.replaceAll("[ìíịỉĩ]", "i");
        str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
        str = str.replaceAll("[ỳýỵỷỹ]", "y");
        str = str.replaceAll("[đ]", "d");
        str = str.replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A");
        str = str.replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E");
        str = str.replaceAll("[ÌÍỊỈĨ]", "I");
        str = str.replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O");
        str = str.replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U");
        str = str.replaceAll("[ỲÝỴỶỸ]", "Y");
        str = str.replaceAll("[Đ]", "D");
        return str;
    }

    public void themHoaDonVaChiTietHoaDon(String ngayMua, double tongTien, String tenDangNhap, DefaultTableModel modelSanPhamMua) {
        Connection connection = getConnection();
        PreparedStatement psHoaDon = null;
        PreparedStatement psChiTietHoaDon = null;
        ResultSet rs = null;

        try {
            // Lấy MaNguoiDung từ TenDangNhap
            String queryMaNguoiDung = "SELECT MaNguoiDung FROM NguoiDung WHERE TenDangNhap = ?";
            psHoaDon = connection.prepareStatement(queryMaNguoiDung);
            psHoaDon.setString(1, tenDangNhap);
            rs = psHoaDon.executeQuery();
            int maNguoiDung = 0;
            if (rs.next()) {
                maNguoiDung = rs.getInt("MaNguoiDung");
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy người dùng!");
                return;
            }

            // Thêm vào bảng HoaDon
            String queryThemHoaDon = "INSERT INTO HoaDon (NgayMua, TongTien, MaNguoiDung) VALUES (?, ?, ?)";
            psHoaDon = connection.prepareStatement(queryThemHoaDon, Statement.RETURN_GENERATED_KEYS);
            psHoaDon.setString(1, ngayMua);
            psHoaDon.setDouble(2, tongTien);
            psHoaDon.setInt(3, maNguoiDung);
            psHoaDon.executeUpdate();

            // Lấy MaHoaDon vừa thêm
            rs = psHoaDon.getGeneratedKeys();
            if (rs.next()) {
                // Lưu mã hóa đơn vào biến toàn cục hoặc biến instance
                setMaHoaDon(rs.getInt(1));
            }
            hoaDon = maHoaDon;
            // Thêm vào bảng ChiTietHoaDon
            String queryThemChiTietHoaDon = "INSERT INTO ChiTietHoaDon (MaHoaDon, MaSach, SoLuong, GiaBan) VALUES (?, ?, ?, ?)";
            psChiTietHoaDon = connection.prepareStatement(queryThemChiTietHoaDon);
            for (int i = 0; i < modelSanPhamMua.getRowCount(); i++) {
                // Lấy thông tin từ modelSanPhamMua
                String tenSach = (String) modelSanPhamMua.getValueAt(i, 1);
                int soLuong = (Integer) modelSanPhamMua.getValueAt(i, 3);

                // Truy vấn để lấy MaSach và GiaBan từ bảng Sach
                String queryMaSachGiaBan = "SELECT MaSach, Gia FROM Sach WHERE TenSach = ?";
                PreparedStatement psMaSachGiaBan = connection.prepareStatement(queryMaSachGiaBan);
                psMaSachGiaBan.setString(1, tenSach);
                ResultSet rsMaSachGiaBan = psMaSachGiaBan.executeQuery();

                // Nếu có kết quả từ truy vấn
                if (rsMaSachGiaBan.next()) {
                    int maSach = rsMaSachGiaBan.getInt("MaSach");
                    double giaBan = rsMaSachGiaBan.getDouble("Gia");

                    // Thêm dữ liệu vào bảng ChiTietHoaDon
                    psChiTietHoaDon.setInt(1, maHoaDon);
                    psChiTietHoaDon.setInt(2, maSach);
                    psChiTietHoaDon.setInt(3, soLuong);
                    psChiTietHoaDon.setDouble(4, giaBan);
                    psChiTietHoaDon.executeUpdate();
                } else {
                    // Xử lý trường hợp không tìm thấy sách trong bảng Sach
                    System.out.println("Không tìm thấy sách có tên: " + tenSach);
                }

                // Đóng kết nối và tài nguyên ResultSet và PreparedStatement
                rsMaSachGiaBan.close();
                psMaSachGiaBan.close();
            }
            //JOptionPane.showMessageDialog(null, "Thêm hóa đơn và chi tiết hóa đơn thành công!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psHoaDon != null) {
                    psHoaDon.close();
                }
                if (psChiTietHoaDon != null) {
                    psChiTietHoaDon.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            }
        }
    }

    public void capNhatSoLuongSach(DefaultTableModel modelSanPhamMua) {
        Connection connection = null;
        PreparedStatement psCapNhatSoLuong = null;

        try {
            connection = getConnection();
            String queryCapNhatSoLuong = "UPDATE Sach SET SoLuong = SoLuong - ? WHERE TenSach = ?";
            psCapNhatSoLuong = connection.prepareStatement(queryCapNhatSoLuong);

            for (int i = 0; i < modelSanPhamMua.getRowCount(); i++) {
                String tenSach = (String) modelSanPhamMua.getValueAt(i, 1); // Lấy tên sách từ model
                int soLuongMua = (Integer) modelSanPhamMua.getValueAt(i, 3); // Lấy số lượng mua từ model

                psCapNhatSoLuong.setInt(1, soLuongMua);
                psCapNhatSoLuong.setString(2, tenSach); // Đặt tên sách vào câu lệnh SQL

                psCapNhatSoLuong.executeUpdate();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        } finally {
            try {
                if (psCapNhatSoLuong != null) {
                    psCapNhatSoLuong.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            }
        }
    }

}
