/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.sql.Timestamp;

public class LayHoaDon {
    private int maHoaDon;
    private Timestamp ngayMua;
    private double tongTien;
    private int maNguoiDung;

    public LayHoaDon(int maHoaDon, Timestamp ngayMua, double tongTien, int maNguoiDung) {
        this.maHoaDon = maHoaDon;
        this.ngayMua = ngayMua;
        this.tongTien = tongTien;
        this.maNguoiDung = maNguoiDung;
    }
    
    public LayHoaDon() {
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Timestamp getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Timestamp ngayMua) {
        this.ngayMua = ngayMua;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }
}