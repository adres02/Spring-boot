package com.example.nds.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.nds.model.Karyawan;
import com.example.nds.model.KaryawanRowMapper;

@Repository
public class KaryawanDAOImpl implements KaryawanDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Karyawan> findAll() {
        String sql = "SELECT * FROM karyawan";
        return jdbcTemplate.query(sql, new KaryawanRowMapper());
    }

    // @Override
    // public List<Karyawan> search(String namaKaryawan, String noHp, LocalDate
    // startDate, LocalDate endDate) {
    // String sql = "SELECT * FROM karyawan WHERE nama_karyawan LIKE ? AND no_hp
    // LIKE ? AND tanggal_masuk_kerja BETWEEN ? AND ?";
    // String query1 = "%" + namaKaryawan + "%";
    // String query2 = "%" + noHp + "%";
    // Object[] params = { query1, query2, startDate, endDate };
    // List<Karyawan> karyawanList = jdbcTemplate.query(sql, params, new
    // KaryawanRowMapper());
    // return karyawanList;
    // }

    @Override
    public List<Karyawan> search(String namaKaryawan, String noHp, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM karyawan WHERE nama_karyawan LIKE ? AND tanggal_masuk_kerja BETWEEN ? AND ?";
        String query1 = "%" + namaKaryawan + "%";
        Object[] params;
        if (noHp == null || noHp.isEmpty()) {
            params = new Object[] { query1, startDate, endDate };
        } else {
            String query2 = "%" + noHp + "%";
            params = new Object[] { query1, startDate, endDate, query2 };
            sql += " AND no_hp LIKE ?";
        }
        List<Karyawan> karyawanList = jdbcTemplate.query(sql, params, new KaryawanRowMapper());
        return karyawanList;
    }

    @Override
    public Karyawan getKaryawanByKode(String kodeKaryawan) {
        String sql = "SELECT * FROM karyawan WHERE kode_karyawan = ?";
        Karyawan karyawan = jdbcTemplate.queryForObject(sql, new Object[] { kodeKaryawan }, new KaryawanRowMapper());
        return karyawan;
    }

    @Override
    public boolean existsByNamaKaryawanIgnoreCase(String namaKaryawan) {
        String sql = "SELECT COUNT(*) FROM karyawan WHERE LOWER(nama_karyawan) = LOWER(?)";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, namaKaryawan);
        return count > 0;
    }

    public class DuplicateNameException extends RuntimeException {
        public DuplicateNameException(String message) {
            super(message);
        }
    }

    // Insert Data
    @Override
    public void insertKaryawan(Karyawan karyawan) {
        String namaKaryawan = karyawan.getNamaKaryawan();
        if (existsByNamaKaryawanIgnoreCase(namaKaryawan)) {
            throw new DuplicateNameException("Nama karyawan sudah digunakan");
        }
        String sql = "INSERT INTO karyawan(kode_karyawan, nama_karyawan, no_hp, tanggal_masuk_kerja, limit_reimbursement, created_date, updated_date) "
                +
                "VALUES (?, ?, ?, ?, ?, current_timestamp, current_timestamp)";
        jdbcTemplate.update(sql, karyawan.getKodeKaryawan(),
                karyawan.getNamaKaryawan(), karyawan.getNoHp(),
                karyawan.getTanggalMasukKerja(), karyawan.getLimitReimbursement());
    }

    // Update Data
    @Override
    public void updateKaryawan(Karyawan karyawan) {
        String namaKaryawan = karyawan.getNamaKaryawan();
        if (existsByNamaKaryawanIgnoreCase(namaKaryawan)) {
            throw new DuplicateNameException("Nama karyawan sudah digunakan");
        }
        String sql = "UPDATE karyawan SET nama_karyawan = ?, no_hp = ?, tanggal_masuk_kerja = ?, limit_reimbursement = ?, updated_date = current_timestamp WHERE kode_karyawan = ?";
        jdbcTemplate.update(sql, karyawan.getNamaKaryawan(), karyawan.getNoHp(), karyawan.getTanggalMasukKerja(),
                karyawan.getLimitReimbursement(), karyawan.getKodeKaryawan());
    }

    // Delete Data
    @Override
    public void deleteKaryawan(String kodeKaryawan) {
        String sql = "DELETE FROM karyawan WHERE kode_karyawan = ?";
        jdbcTemplate.update(sql, kodeKaryawan);
    }

}
