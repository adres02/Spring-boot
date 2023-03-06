package com.example.nds.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Karyawan {

    private String kodeKaryawan;
    private String namaKaryawan;
    private LocalDate tanggalMasukKerja;
    private String noHp;
    private BigDecimal limitReimbursement;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    public void setKodeKaryawan(String kodeKaryawan) {
        this.kodeKaryawan = kodeKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public void setTanggalMasukKerja(LocalDate tanggalMasukKerja) {
        this.tanggalMasukKerja = tanggalMasukKerja;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setLimitReimbursement(BigDecimal limitReimbursement) {
        this.limitReimbursement = limitReimbursement;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getKodeKaryawan() {
        return kodeKaryawan;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public LocalDate getTanggalMasukKerja() {
        return tanggalMasukKerja;
    }

    public String getNoHp() {
        return noHp;
    }

    public BigDecimal getLimitReimbursement() {
        return limitReimbursement;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public Karyawan(String kodeKaryawan, String namaKaryawan, LocalDate tanggalMasukKerja, String noHp,
            BigDecimal limitReimbursement, LocalDate createdDate, LocalDate updatedDate) {
        this.kodeKaryawan = kodeKaryawan;
        this.namaKaryawan = namaKaryawan;
        this.tanggalMasukKerja = tanggalMasukKerja;
        this.noHp = noHp;
        this.limitReimbursement = limitReimbursement;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Karyawan() {

    }

    @Override
    public String toString() {
        return "Tutorial [kodeKaryawan=" + kodeKaryawan + ", namaKaryawan=" + namaKaryawan + ", tanggalMasukKerja="
                + tanggalMasukKerja + ", noHp="
                + noHp + ", limitReimbursement="
                + limitReimbursement + ", createdDate="
                + createdDate + ", updatedDate="
                + updatedDate + "]";
    }
}
