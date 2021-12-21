package com.example.qrcovax.model;

public class User {
    private String id, cccd, ten, ngaysinh, gioitinh, email, sdt,
            quanhuyen, phuongxa, todanpho;

    public User() {
    }

    public User(String id, String cccd, String ten, String ngaysinh, String gioitinh, String email, String sdt, String quanhuyen, String phuongxa, String todanpho) {
        this.id = id;
        this.cccd = cccd;
        this.ten = ten;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.email = email;
        this.sdt = sdt;
        this.quanhuyen = quanhuyen;
        this.phuongxa = phuongxa;
        this.todanpho = todanpho;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getQuanhuyen() {
        return quanhuyen;
    }

    public void setQuanhuyen(String quanhuyen) {
        this.quanhuyen = quanhuyen;
    }

    public String getPhuongxa() {
        return phuongxa;
    }

    public void setPhuongxa(String phuongxa) {
        this.phuongxa = phuongxa;
    }

    public String getTodanpho() {
        return todanpho;
    }

    public void setTodanpho(String todanpho) {
        this.todanpho = todanpho;
    }
}
