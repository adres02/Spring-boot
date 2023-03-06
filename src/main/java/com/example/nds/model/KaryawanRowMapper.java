package com.example.nds.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

public class KaryawanRowMapper implements RowMapper<Karyawan> {

    @Override
    public Karyawan mapRow(ResultSet rs, int rowNum) throws SQLException {
        Karyawan karyawan = new Karyawan();
        karyawan.setKodeKaryawan(rs.getString("kode_karyawan"));
        karyawan.setNamaKaryawan(rs.getString("nama_karyawan"));
        karyawan.setNoHp(rs.getString("no_hp"));
        karyawan.setTanggalMasukKerja(rs.getDate("tanggal_masuk_kerja").toLocalDate());
        karyawan.setLimitReimbursement(rs.getBigDecimal("limit_reimbursement"));

        karyawan.setCreatedDate(
                LocalDate.parse(rs.getString("created_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        karyawan.setUpdatedDate(
                LocalDate.parse(rs.getString("updated_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return karyawan;
    }
}
