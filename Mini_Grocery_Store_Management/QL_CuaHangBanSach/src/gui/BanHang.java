/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static dao.SQLServerProvider.getConnection;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import dao.BanHangDAO;

import pojo.Sach;

/**
 *
 * @author MSII
 */
public class BanHang extends javax.swing.JFrame {

    /**
     * Creates new form BanHang
     */
    public BanHang() {
        initComponents(); // Gọi phương thức khởi tạo giao diện
        this.setLocationRelativeTo(null);

        setButtonBackground(new java.awt.Color(190, 160, 190));
        getContentPane().setBackground(new Color(255, 229, 229)); // màu cho form

        // Thiết lập model cho bảng sách
        tblSach.setModel(new NonEditableTableModel(new Object[][]{}, new String[]{
            "Tên sách", "Mô tả sách", "Tác giả", "Giá", "Số lượng"
        }));

        loadSachData();  // Gọi phương thức để tải dữ liệu sách và hiển thị trên bảng
        loadDanhMucIntoComboBox();  // Đảm bảo rằng chỉ gọi một lần để khởi tạo dữ liệu
        addTableSelectionListener();

        // Tạo đối tượng TimeUpdater và bắt đầu cập nhật thời gian
        BanHangDAO timeUpdater = new BanHangDAO(lblNgayThangNam);
        timeUpdater.batDauCapNhatThoiGian();

        // Hiển thị lời chào tới người dùng đã đăng nhập
        lblXinChao.setText("Xin chào, " + DangNhap.loggedInUsername);

        // Thiết lập model cho bảng sản phẩm mua
        tblSanPhamMua.setModel(new NonEditableTableModel(new Object[][]{}, new String[]{
            "STT", "Sách", "Giá sách", "Số Lượng", "Thành tiền"
        }));

        // Thiết lập model cho bảng hóa đơn
        tblHoaDon.setModel(new NonEditableTableModel(new Object[][]{}, new String[]{
            "STT", "Sách", "Số lượng", "Thành tiền", "Ngày bán"
        }));
    }

    private void setButtonBackground(Color color) {
        Component[] components = getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(color);
            }
        }
    }

    // Lớp con của DefaultTableModel để ngăn chặn việc chỉnh sửa nội dung
    private class NonEditableTableModel extends DefaultTableModel {

        public NonEditableTableModel(Object[][] data, String[] columns) {
            super(data, columns);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Không cho phép chỉnh sửa
        }
    }

    private void addTableSelectionListener() {
        tblSach.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // Kiểm tra xem lựa chọn có đang được thực hiện
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tblSach.getSelectedRow();
                    if (selectedRow != -1) { // Kiểm tra xem đã chọn hàng nào chưa
                        // Lấy dữ liệu từ hàng đã chọn và hiển thị lên ô text
                        String tenSach = tblSach.getValueAt(selectedRow, 0).toString();
                        String gia = tblSach.getValueAt(selectedRow, 3).toString();
                        txtTenSach.setText(tenSach);
                        txtGia.setText(gia);
                    }
                }
            }
        });
    }

    private void loadDanhMucIntoComboBox() {
        BanHangDAO danhMucDao = new BanHangDAO();
        try {
            List<String> danhMucList = danhMucDao.getAllDanhMuc();
            cbxDanhMuc.removeAllItems(); // Xóa các mục cũ trong combobox
            for (String tenDanhMuc : danhMucList) {
                cbxDanhMuc.addItem(tenDanhMuc); // Thêm tên danh mục vào combobox
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu danh mục: " + e.getMessage());
        }
    }

    private void loadSachData() {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        BanHangDAO sachDao = new BanHangDAO();
        try {
            List<Sach> sachList = sachDao.getAllSach();
            for (Sach sach : sachList) {
                model.addRow(new Object[]{
                    sach.getTenSach(),
                    sach.getMoTaSach(),
                    sach.getTacGia(),
                    sach.getGia(),
                    sach.getSoLuong()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }
    }

    private void filterSachByDanhMuc(String tenDanhMuc) {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        BanHangDAO sachDao = new BanHangDAO();
        try {
            List<Sach> sachList = sachDao.getSachByDanhMuc(tenDanhMuc);
            for (Sach sach : sachList) {
                model.addRow(new Object[]{
                    sach.getTenSach(),
                    sach.getMoTaSach(),
                    sach.getTacGia(),
                    sach.getGia(),
                    sach.getSoLuong()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc sách theo danh mục: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblXinChao = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNgayThangNam = new javax.swing.JLabel();
        lblTongThanhTien = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        txtSoLuongMua = new javax.swing.JTextField();
        btnThemSach = new javax.swing.JButton();
        cbxDanhMuc = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamMua = new javax.swing.JTable();
        btnThemHoaDon = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnInHoaDon = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        btnLamMoiSanPhamMua = new javax.swing.JButton();
        btnLamMoiSach1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblXinChao.setText("jLabel2");

        jLabel3.setText("Tên sách");

        jLabel4.setText("Giá");

        jLabel5.setText("Số lượng mua");

        lblNgayThangNam.setText("jLabel6");

        lblTongThanhTien.setText("Tổng thành tiền");

        txtTenSach.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtTenSach.setEnabled(false);
        txtTenSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSachActionPerformed(evt);
            }
        });

        txtGia.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtGia.setEnabled(false);
        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });

        txtSoLuongMua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongMuaActionPerformed(evt);
            }
        });

        btnThemSach.setText("Thêm sách");
        btnThemSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSachActionPerformed(evt);
            }
        });

        cbxDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDanhMucActionPerformed(evt);
            }
        });

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblSach);

        tblSanPhamMua.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblSanPhamMua);

        btnThemHoaDon.setText("Thêm hóa đơn");
        btnThemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHoaDonActionPerformed(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblHoaDon);

        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        btnLamMoiSanPhamMua.setText("Làm mới");
        btnLamMoiSanPhamMua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiSanPhamMuaActionPerformed(evt);
            }
        });

        btnLamMoiSach1.setText("Tất cả");
        btnLamMoiSach1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiSach1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("BÁN HÀNG");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblXinChao)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNgayThangNam)
                                .addGap(202, 202, 202))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(495, 495, 495)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(btnLamMoiSanPhamMua)
                        .addGap(75, 75, 75))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnThemSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cbxDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLamMoiSach1))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4))
                                    .addGap(62, 62, 62)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtTenSach, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                                        .addComponent(txtGia)
                                        .addComponent(txtSoLuongMua, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(770, 770, 770)
                        .addComponent(btnThemHoaDon)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                                .addComponent(btnDangXuat)
                                .addGap(158, 158, 158)
                                .addComponent(btnInHoaDon))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(175, 175, 175)
                                .addComponent(lblTongThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDangXuat, btnInHoaDon, btnLamMoiSach1, btnLamMoiSanPhamMua, btnThemHoaDon, btnThemSach});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblXinChao)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNgayThangNam)
                                .addComponent(btnLamMoiSanPhamMua)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtSoLuongMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addComponent(btnThemSach, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(66, 66, 66)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(cbxDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnLamMoiSach1, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTongThanhTien)
                                    .addComponent(btnThemHoaDon))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInHoaDon)
                    .addComponent(btnDangXuat))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDangXuat, btnInHoaDon, btnLamMoiSach1, btnLamMoiSanPhamMua, btnThemHoaDon, btnThemSach});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSachActionPerformed

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed

    private void txtSoLuongMuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongMuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongMuaActionPerformed

    private void btnThemSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSachActionPerformed
        // Kiểm tra dữ liệu nhập vào
        if (BanHangDAO.duLieuHopLe(txtTenSach.getText(), txtGia.getText(), txtSoLuongMua.getText())) {
            String tenSach = txtTenSach.getText();
            double gia = Double.parseDouble(txtGia.getText());
            int soLuongMua = Integer.parseInt(txtSoLuongMua.getText());
            double thanhTien = BanHangDAO.tinhThanhTien(soLuongMua, gia);

            // Kiểm tra sản phẩm đã tồn tại trong bảng chưa
            if (BanHangDAO.kiemTraSanPhamTonTai(tblSanPhamMua, tenSach)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã được thêm vào bảng.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                // Thêm dữ liệu vào bảng
                DefaultTableModel model = (DefaultTableModel) tblSanPhamMua.getModel();
                model.addRow(new Object[]{
                    model.getRowCount() + 1,
                    tenSach,
                    gia,
                    soLuongMua,
                    thanhTien
                });

                // Cập nhật tổng thành tiền
                BanHangDAO.capNhatTongThanhTien(tblSanPhamMua, lblTongThanhTien);
            }
        }
    }//GEN-LAST:event_btnThemSachActionPerformed

    private void btnLamMoiSach1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiSach1ActionPerformed
        // TODO add your handling code here:
        loadSachData();
        txtSoLuongMua.setText("");

    }//GEN-LAST:event_btnLamMoiSach1ActionPerformed

    private void btnLamMoiSanPhamMuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiSanPhamMuaActionPerformed
        // TODO add your handling code here:
        // Làm mới dữ liệu trong bảng tblSanPhamMua
        DefaultTableModel modelSanPhamMua = (DefaultTableModel) tblSanPhamMua.getModel();
        modelSanPhamMua.setRowCount(0); // Xóa tất cả các hàng trong bảng

        DefaultTableModel modelHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        modelHoaDon.setRowCount(0); // Xóa tất cả các hàng trong bảng
        txtSoLuongMua.setText("");

        // Cập nhật label tổng thành tiền về trạng thái ban đầu
        lblTongThanhTien.setText("Tổng thành tiền");
    }//GEN-LAST:event_btnLamMoiSanPhamMuaActionPerformed

    private void btnThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHoaDonActionPerformed
        if (tblSanPhamMua.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Bảng sản phẩm mua đang trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            if (tblHoaDon.getRowCount() > 0) {
                JOptionPane.showMessageDialog(this, "Bảng hóa đơn đã có dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                int response = JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán và xuất hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    DefaultTableModel modelSanPhamMua = (DefaultTableModel) tblSanPhamMua.getModel();
                    DefaultTableModel modelHoaDon = (DefaultTableModel) tblHoaDon.getModel();
                    int rowCount = modelSanPhamMua.getRowCount();

                    for (int i = 0; i < rowCount; i++) {
                        int stt = (Integer) modelSanPhamMua.getValueAt(i, 0);
                        String sach = (String) modelSanPhamMua.getValueAt(i, 1);
                        int soLuong = (Integer) modelSanPhamMua.getValueAt(i, 3);
                        double thanhTien = (Double) modelSanPhamMua.getValueAt(i, 4);
                        String ngayBan = lblNgayThangNam.getText();

                        modelHoaDon.addRow(new Object[]{stt, sach, soLuong, thanhTien, ngayBan});
                    }

                    modelHoaDon.addRow(new Object[]{"", "", "", "", ""});

                    double tongThanhTien = 0;
                    for (int i = 0; i < rowCount; i++) {
                        double thanhTien = (Double) modelSanPhamMua.getValueAt(i, 4);
                        tongThanhTien += thanhTien;
                    }

                    String formattedTongThanhTien = String.format("%,.0f VNĐ.", tongThanhTien);
                    modelHoaDon.addRow(new Object[]{"Thực hiện: " + DangNhap.loggedInUsername, "Tổng cộng:", "", formattedTongThanhTien, ""});
                    lblTongThanhTien.setText("Tổng thành tiền: " + formattedTongThanhTien);

                    BanHangDAO hdD = new BanHangDAO();
                    hdD.themHoaDonVaChiTietHoaDon(lblNgayThangNam.getText(), tongThanhTien, DangNhap.loggedInUsername, modelSanPhamMua);

                    hdD.capNhatSoLuongSach(modelSanPhamMua);

                    loadSachData();
                }
            }
        }
    }//GEN-LAST:event_btnThemHoaDonActionPerformed


    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        // Hiển thị hộp thoại xác nhận
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);

        // Nếu người dùng chọn Yes
        if (choice == JOptionPane.YES_OPTION) {
            // Đóng form hiện tại
            this.dispose();

            // Mở form DangNhap
            DangNhap dangNhapForm = new DangNhap();
            dangNhapForm.setVisible(true);
        }
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        // Kiểm tra nếu bảng tblHoaDon trống
        if (tblHoaDon.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Bảng hóa đơn đang trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            // Nếu không trống, tiến hành xuất hóa đơn
            int response = JOptionPane.showConfirmDialog(this, "Xác nhận in hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                // Lấy mô hình dữ liệu từ bảng tblHoaDon
                TableModel modelHoaDon = tblHoaDon.getModel();
                // Gọi phương thức generatePDF từ gói DAO để tạo và lưu tệp PDF từ mô hình dữ liệu
                // Khởi tạo đối tượng của class entity
                BanHangDAO.generatePDF(modelHoaDon); // truyền vào mã hóa đơn từ nơi nào đó
            }
        }
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void cbxDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDanhMucActionPerformed
        // TODO add your handling code here:
        String selectedDanhMuc = (String) cbxDanhMuc.getSelectedItem();
        if (selectedDanhMuc != null) {
            filterSachByDanhMuc(selectedDanhMuc);
        }
    }//GEN-LAST:event_cbxDanhMucActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLamMoiSach1;
    private javax.swing.JButton btnLamMoiSanPhamMua;
    private javax.swing.JButton btnThemHoaDon;
    private javax.swing.JButton btnThemSach;
    private javax.swing.JComboBox<String> cbxDanhMuc;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblNgayThangNam;
    private javax.swing.JLabel lblTongThanhTien;
    private javax.swing.JLabel lblXinChao;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSach;
    private javax.swing.JTable tblSanPhamMua;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtSoLuongMua;
    private javax.swing.JTextField txtTenSach;
    // End of variables declaration//GEN-END:variables
}
