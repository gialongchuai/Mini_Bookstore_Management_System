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
public class ChiTietHoaDon {
    private int maHoaDon;
    private int maSach;
    private int soLuong;
    private double giaBan;

    public ChiTietHoaDon(int maHoaDon, int maSach, int soLuong, double giaBan) {
        this.maHoaDon = maHoaDon;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
}
