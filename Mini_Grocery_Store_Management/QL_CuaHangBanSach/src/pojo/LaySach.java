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
public class LaySach {

    private int maSach;
    private String tenSach;
    private String moTaSach;
    private String tacGia;
    private int gia;
    private int soLuong;
    private int maDanhMuc;

    public LaySach(int maSach, String tenSach, String moTaSach, String tacGia, int gia, int soLuong, int maDanhMuc) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.moTaSach = moTaSach;
        this.tacGia = tacGia;
        this.gia = gia;
        this.soLuong = soLuong;
        this.maDanhMuc = maDanhMuc;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMoTaSach() {
        return moTaSach;
    }

    public void setMoTaSach(String moTaSach) {
        this.moTaSach = moTaSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    @Override
    public String toString() {
        return "Sach{"
                + "maSach=" + maSach
                + ", tenSach='" + tenSach + '\''
                + ", moTaSach='" + moTaSach + '\''
                + ", tacGia='" + tacGia + '\''
                + ", gia=" + gia
                + ", soLuong=" + soLuong
                + ", maDanhMuc=" + maDanhMuc
                + '}';
    }
}
