package com.example.nds.dao;

import java.time.LocalDate;
import java.util.List;

import com.example.nds.model.Karyawan;

public interface KaryawanDAO {

    public List<Karyawan> findAll();

    public List<Karyawan> search(String namaKaryawan, String noHp, LocalDate startDate, LocalDate endDate);

    Karyawan getKaryawanByKode(String kodeKaryawan);

    public boolean existsByNamaKaryawanIgnoreCase(String namaKaryawan);

    public void insertKaryawan(Karyawan karyawan);

    public void deleteKaryawan(String kodeKaryawan);

    public void updateKaryawan(Karyawan karyawan);
}
