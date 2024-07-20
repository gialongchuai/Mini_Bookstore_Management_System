/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author MSII
 */
public class LayNguoiDung {

    private int maNguoiDung;
    private String tenNguoiDung;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;

    public LayNguoiDung(int maNguoiDung, String tenNguoiDung, String email, String soDienThoai, String diaChi, String tenDangNhap, String matKhau, String vaiTro) {
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    @Override
    public String toString() {
        return "NguoiDung{"
                + "maNguoiDung=" + maNguoiDung
                + ", tenNguoiDung='" + tenNguoiDung + '\''
                + ", email='" + email + '\''
                + ", soDienThoai='" + soDienThoai + '\''
                + ", diaChi='" + diaChi + '\''
                + ", tenDangNhap='" + tenDangNhap + '\''
                + ", matKhau='" + matKhau + '\''
                + ", vaiTro='" + vaiTro + '\''
                + '}';
    }
}
