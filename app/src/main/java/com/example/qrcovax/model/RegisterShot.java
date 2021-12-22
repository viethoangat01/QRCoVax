package com.example.qrcovax.model;

public class RegisterShot {
    private String cccd, ten, qr_id;
    private long todanpho_id, thoigianbatdau, thoigianketthuc;

    public RegisterShot() {
    }

    public RegisterShot(String cccd, String ten, String qr_id, long todanpho_id, long thoigianbatdau, long thoigianketthuc) {
        this.cccd = cccd;
        this.ten = ten;
        this.qr_id = qr_id;
        this.todanpho_id = todanpho_id;
        this.thoigianbatdau = thoigianbatdau;
        this.thoigianketthuc = thoigianketthuc;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getQr_id() {
        return qr_id;
    }

    public void setQr_id(String qr_id) {
        this.qr_id = qr_id;
    }

    public long getTodanpho_id() {
        return todanpho_id;
    }

    public void setTodanpho_id(long todanpho_id) {
        this.todanpho_id = todanpho_id;
    }

    public long getThoigianbatdau() {
        return thoigianbatdau;
    }

    public void setThoigianbatdau(long thoigianbatdau) {
        this.thoigianbatdau = thoigianbatdau;
    }

    public long getThoigianketthuc() {
        return thoigianketthuc;
    }

    public void setThoigianketthuc(long thoigianketthuc) {
        this.thoigianketthuc = thoigianketthuc;
    }
}
