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
public class LayDanhMucSach {

    private int maDanhMuc;
    private String tenDanhMuc;
    private String moTaDanhMuc;

    public LayDanhMucSach(int maDanhMuc, String tenDanhMuc, String moTaDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.moTaDanhMuc = moTaDanhMuc;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMoTaDanhMuc() {
        return moTaDanhMuc;
    }

    public void setMoTaDanhMuc(String moTaDanhMuc) {
        this.moTaDanhMuc = moTaDanhMuc;
    }

    @Override
    public String toString() {
        return "DanhMucSach{"
                + "maDanhMuc=" + maDanhMuc
                + ", tenDanhMuc='" + tenDanhMuc + '\''
                + ", moTaDanhMuc='" + moTaDanhMuc + '\''
                + '}';
    }
}
