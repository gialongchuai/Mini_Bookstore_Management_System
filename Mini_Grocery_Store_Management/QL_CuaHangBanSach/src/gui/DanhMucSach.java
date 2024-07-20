/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.SQLServerProvider;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import pojo.LayDanhMucSach;
import dao.DanhMucSachDAO;

/**
 *
 * @author MSII
 */
public class DanhMucSach extends javax.swing.JFrame {

    /**
     * Creates new form DanhMucSach
     */
    public DanhMucSach() {
        initComponents();
        this.setLocationRelativeTo(null);
        setButtonBackground(new java.awt.Color(190, 160, 190));
        getContentPane().setBackground(new Color(255, 229, 229)); // màu cho form

        // Thiết lập model cho bảng sản phẩm mua
        tblDanhMucSach.setModel(new DanhMucSach.NonEditableTableModel(new Object[][]{}, new String[]{
            "Mã danh mục", "Tên danh mục", "Mô tả danh mục"
        }));

        // Ngăn chặn chỉnh sửa các ô
        tblDanhMucSach.setDefaultEditor(Object.class, null);

        // Ngăn chặn chọn ô và double click
        tblDanhMucSach.setCellSelectionEnabled(false);
        tblDanhMucSach.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    e.consume(); // Không thực hiện hành động nào khi double click
                }
            }
        });

        // Load dữ liệu vào bảng
        loadDataToTable();
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

    // Phương thức để load dữ liệu từ cơ sở dữ liệu lên bảng
    private void loadDataToTable() {
        List<Object[]> danhSachDanhMuc = danhMucSachDAO.layDanhSachDanhMuc();
        DefaultTableModel model = (DefaultTableModel) tblDanhMucSach.getModel();

        // Xóa tất cả các dòng cũ trong bảng
        model.setRowCount(0);

        // Thêm dữ liệu từ danh sách vào bảng
        for (Object[] row : danhSachDanhMuc) {
            model.addRow(row);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTaiKhoan = new javax.swing.JButton();
        btnSach = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDanhMucSach = new javax.swing.JTable();
        txtMaDanhMuc = new javax.swing.JTextField();
        txtTenDanhMuc = new javax.swing.JTextField();
        txtMoTaDanhMuc = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnLamMoiTbl = new javax.swing.JButton();
        btnTuDong = new javax.swing.JButton();
        btnLamMoiTxt = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnTaiKhoan.setText("Tài khoản");
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });

        btnSach.setText("Sách");
        btnSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSachActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("DANH MỤC SÁCH");

        jLabel2.setText("Mã danh mục");

        jLabel3.setText("Tên danh mục");

        jLabel4.setText("Mô tả danh mục");

        tblDanhMucSach.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblDanhMucSach);

        txtMaDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaDanhMucActionPerformed(evt);
            }
        });

        txtTenDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDanhMucActionPerformed(evt);
            }
        });

        txtMoTaDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMoTaDanhMucActionPerformed(evt);
            }
        });

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnLamMoiTbl.setText("Làm mới");
        btnLamMoiTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiTblActionPerformed(evt);
            }
        });

        btnTuDong.setText("Tự động");
        btnTuDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTuDongActionPerformed(evt);
            }
        });

        btnLamMoiTxt.setText("Làm mới");
        btnLamMoiTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiTxtActionPerformed(evt);
            }
        });

        btnHoaDon.setText("Hóa đơn");
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSach))
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnThem)
                                    .addGap(40, 40, 40)
                                    .addComponent(btnXoa)
                                    .addGap(36, 36, 36)
                                    .addComponent(btnSua)
                                    .addGap(54, 54, 54)
                                    .addComponent(btnLuu)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnLamMoiTxt)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel2))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                                            .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMoTaDanhMuc))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTuDong)))
                        .addGap(0, 50, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                        .addComponent(btnLamMoiTbl))
                    .addComponent(jScrollPane4))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(799, 799, 799)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHoaDon, btnSach, btnTaiKhoan});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDangXuat, btnLuu, btnSua, btnThem, btnXoa});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLamMoiTbl, btnLamMoiTxt, btnTimKiem, btnTuDong});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTimKiem)
                            .addComponent(btnLamMoiTbl)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLamMoiTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTuDong))
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtMoTaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(btnSach)))
                        .addGap(38, 38, 38)
                        .addComponent(btnHoaDon)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnLuu))
                .addGap(18, 18, 18)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHoaDon, btnSach, btnTaiKhoan});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDangXuat, btnLuu, btnSua, btnThem, btnXoa});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnLamMoiTbl, btnLamMoiTxt, btnTimKiem, btnTuDong});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        // TODO add your handling code here:
        // Hiển thị hộp thoại xác nhận
        int choice = JOptionPane.showConfirmDialog(this, "Mở danh sách tài khoản?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);

        // Nếu người dùng chọn Yes
        if (choice == JOptionPane.YES_OPTION) {
            // Đóng form hiện tại
            this.dispose();

            // Mở form DangNhap
            TaiKhoan dangNhapForm = new TaiKhoan();
            dangNhapForm.setVisible(true);
        }
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void btnSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSachActionPerformed
        // TODO add your handling code here:
        // Hiển thị hộp thoại xác nhận
        int choice = JOptionPane.showConfirmDialog(this, "Mở tất cả sách?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);

        // Nếu người dùng chọn Yes
        if (choice == JOptionPane.YES_OPTION) {
            // Đóng form hiện tại
            this.dispose();

            // Mở form DangNhap
            Sach dangNhapForm = new Sach();
            dangNhapForm.setVisible(true);
        }
    }//GEN-LAST:event_btnSachActionPerformed

    private DanhMucSachDAO danhMucSachDAO = new DanhMucSachDAO();

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        String maDanhMuc = txtMaDanhMuc.getText();
        String tenDanhMuc = txtTenDanhMuc.getText();
        String moTaDanhMuc = txtMoTaDanhMuc.getText();

        // Kiểm tra xem các trường có được nhập đầy đủ không
        if (maDanhMuc.isEmpty() || tenDanhMuc.isEmpty() || moTaDanhMuc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin danh mục!");
            return;
        }

        // Hiển thị hộp thoại xác nhận
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm mới danh mục này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            // Kiểm tra xem mã danh mục đã tồn tại chưa
            if (danhMucSachDAO.maDanhMucTonTai(maDanhMuc)) {
                JOptionPane.showMessageDialog(this, "Mã danh mục đã tồn tại!");
                return;
            }

            // Kiểm tra xem tên danh mục đã tồn tại chưa
            if (danhMucSachDAO.tenDanhMucTonTai(tenDanhMuc)) {
                JOptionPane.showMessageDialog(this, "Tên danh mục đã tồn tại!");
                return;
            }

            // Thêm dữ liệu vào cơ sở dữ liệu
            if (danhMucSachDAO.themDanhMuc(maDanhMuc, tenDanhMuc, moTaDanhMuc)) {
                JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");

                // Gọi hàm loadDataToTable() để cập nhật bảng dữ liệu
                loadDataToTable();

                // Gọi hàm clearFields() để xóa các trường nhập liệu
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm danh mục thất bại!");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        // Kiểm tra xem các ô nhập liệu có dữ liệu hay không
        String maDanhMuc = txtMaDanhMuc.getText().trim();
        String tenDanhMuc = txtTenDanhMuc.getText().trim();
        String moTaDanhMuc = txtMoTaDanhMuc.getText().trim();
        if (maDanhMuc.isEmpty() || tenDanhMuc.isEmpty() || moTaDanhMuc.isEmpty()) {
            // Nếu có ô nhập liệu trống, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một danh mục từ bảng để xóa!");
            return;
        }

        // Hiển thị hộp thoại xác nhận xóa
        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa danh mục này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Gọi phương thức xóa trong DanhMucSachDAO
            DanhMucSachDAO danhMucSachDAO = new DanhMucSachDAO();
            boolean success = danhMucSachDAO.deleteDanhMuc(maDanhMuc);

            if (success) {
                JOptionPane.showMessageDialog(null, "Xóa danh mục thành công!");
                // Cập nhật lại bảng hoặc thực hiện các công việc khác sau khi xóa thành công
                loadDataToTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Xóa danh mục không thành công do xung đột dữ liệu!");
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private int txtMaDanhMucDB;
    private String txtTenDanhMucDb;

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblDanhMucSach.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng danh mục từ bảng để sửa!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblDanhMucSach.getModel();
        int maDanhMuc = (int) model.getValueAt(selectedRow, 0);
        String tenDanhMuc = (String) model.getValueAt(selectedRow, 1);
        String moTaDanhMuc = (String) model.getValueAt(selectedRow, 2);

        txtMaDanhMucDB = maDanhMuc;
        txtTenDanhMucDb = tenDanhMuc;

        txtMaDanhMuc.setText(String.valueOf(maDanhMuc));
        txtTenDanhMuc.setText(tenDanhMuc);
        txtMoTaDanhMuc.setText(moTaDanhMuc);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        String tenDanhMuc = txtTenDanhMuc.getText().trim();
        String moTaDanhMuc = txtMoTaDanhMuc.getText().trim();
        String maDanhMuc = txtMaDanhMuc.getText().trim();

        if (tenDanhMuc.isEmpty() || moTaDanhMuc.isEmpty() || maDanhMuc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin danh mục!");
            return;
        }

        int maDanhMucInput = Integer.parseInt(txtMaDanhMuc.getText().trim());
        if (maDanhMucInput != txtMaDanhMucDB) {
            JOptionPane.showMessageDialog(null, "Không thể thay đổi mã danh mục!");
            return;
        }

        DanhMucSachDAO danhMucSachDAO = new DanhMucSachDAO();
        boolean success = danhMucSachDAO.updateDanhMucSach(maDanhMucInput, tenDanhMuc, moTaDanhMuc);

        if (success) {
            JOptionPane.showMessageDialog(null, "Dữ liệu đã được cập nhật thành công!");
            loadDataToTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật dữ liệu!");
        }
    }//GEN-LAST:event_btnLuuActionPerformed

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

    private void txtMaDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaDanhMucActionPerformed

    private void txtTenDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDanhMucActionPerformed

    private void txtMoTaDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMoTaDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMoTaDanhMucActionPerformed

    private void btnLamMoiTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiTblActionPerformed
        // TODO add your handling code here:
        loadDataToTable();
        txtTimKiem.setText("");
    }//GEN-LAST:event_btnLamMoiTblActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        timKiem();

    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            // Nếu ô tìm kiếm trống, hiển thị thông báo yêu cầu nhập từ khóa
            JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khóa tìm kiếm.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblDanhMucSach.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        try {
            List<Object[]> results = DanhMucSachDAO.timKiem(keyword);
            for (Object[] row : results) {
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private void btnLamMoiTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiTxtActionPerformed
        // TODO add your handling code here:
        clearFields();
    }//GEN-LAST:event_btnLamMoiTxtActionPerformed

    private void btnTuDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTuDongActionPerformed
        // TODO add your handling code here:
        try {
            int newNumber = DanhMucSachDAO.tuDongMaDanhMuc();
            txtMaDanhMuc.setText(Integer.toString(newNumber));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tự động điền số vào MaDanhMuc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTuDongActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
        // Hiển thị hộp thoại xác nhận
        int choice = JOptionPane.showConfirmDialog(this, "Mở hóa đơn sách?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);

        // Nếu người dùng chọn Yes
        if (choice == JOptionPane.YES_OPTION) {
            // Đóng form hiện tại
            this.dispose();

            // Mở form DangNhap
            HoaDon dangNhapForm = new HoaDon();
            dangNhapForm.setVisible(true);
        }
    }//GEN-LAST:event_btnHoaDonActionPerformed
// Hàm để làm trống các ô nhập liệu

    private void clearFields() {
        txtMaDanhMuc.setText("");
        txtMoTaDanhMuc.setText("");
        txtTenDanhMuc.setText("");
    }

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
            java.util.logging.Logger.getLogger(DanhMucSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DanhMucSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DanhMucSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DanhMucSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanhMucSach().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnLamMoiTbl;
    private javax.swing.JButton btnLamMoiTxt;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSach;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTuDong;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblDanhMucSach;
    private javax.swing.JTextField txtMaDanhMuc;
    private javax.swing.JTextField txtMoTaDanhMuc;
    private javax.swing.JTextField txtTenDanhMuc;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}