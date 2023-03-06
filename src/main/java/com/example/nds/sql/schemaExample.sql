CREATE TABLE karyawan (
  kode_karyawan VARCHAR(6) PRIMARY KEY,
  nama_karyawan VARCHAR(50) NOT NULL,
  tanggal_masuk_kerja DATE NOT NULL,
  no_hp VARCHAR(20) NOT NULL,
  limit_reimbursement NUMERIC(15,2) NOT NULL,
  created_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION generate_kode_karyawan() 
RETURNS TRIGGER AS $$ 
DECLARE 
   counter INTEGER; 
BEGIN 
   SELECT COUNT(*) INTO counter FROM karyawan; 
   NEW.kode_karyawan := 'K-' || LPAD(CAST(counter+1 AS TEXT), 3, '0'); 
   RETURN NEW; 
END; 
$$ LANGUAGE plpgsql;

CREATE TRIGGER kode_karyawan_trigger
  BEFORE INSERT ON karyawan
  FOR EACH ROW
  EXECUTE FUNCTION generate_kode_karyawan();
