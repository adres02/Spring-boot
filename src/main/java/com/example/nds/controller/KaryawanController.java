package com.example.nds.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nds.dao.KaryawanDAO;
import com.example.nds.dao.KaryawanDAOImpl.DuplicateNameException;
import com.example.nds.model.Karyawan;

@Controller
public class KaryawanController {

    @Autowired
    private KaryawanDAO karyawanDAO;

    // display data
    @GetMapping("/")
    public String index(Model model) {
        List<Karyawan> karyawanList = karyawanDAO.findAll();
        model.addAttribute("karyawanList", karyawanList);
        return "index";
    }

    @GetMapping("/tambah")
    public String tambah(Model model, @RequestParam(required = false) String error) {
        Karyawan karyawan = new Karyawan();
        model.addAttribute("karyawan", karyawan);
        model.addAttribute("error", error);
        return "tambahKaryawan";
    }

    @PostMapping("/simpan")
    public String submitForm(@ModelAttribute Karyawan karyawan) {

        LocalDate now = LocalDate.now();
        LocalDate tanggalMasukKerja = karyawan.getTanggalMasukKerja();

        // validasi tanggal masuk kerja
        if (tanggalMasukKerja.isBefore(now.minusMonths(2)) || tanggalMasukKerja.isAfter(now.plusMonths(3))) {
            return "redirect:/tambah?error=Tanggal Masuk Kerja tidak valid";
        }

        // Validasi nama karyawan
        try {
            karyawanDAO.insertKaryawan(karyawan);
        } catch (DuplicateNameException e) {
            return "redirect:/tambah?error=Nama karyawan sudah digunakan";
        }
        return "redirect:/";
    }

    // Update Data
    @GetMapping("/edit/{kodeKaryawan}")
    public String EditForm(@PathVariable String kodeKaryawan, Model model,
            @RequestParam(required = false) String error) {
        Karyawan karyawan = karyawanDAO.getKaryawanByKode(kodeKaryawan);
        model.addAttribute("karyawan", karyawan);
        model.addAttribute("error", error);
        return "editKaryawan";
    }

    @PostMapping("/update")
    public String updateKaryawan(@ModelAttribute("karyawan") Karyawan karyawan, RedirectAttributes attributes) {
        LocalDate now = LocalDate.now();
        LocalDate tanggalMasukKerja = karyawan.getTanggalMasukKerja();

        // validasi tanggal masuk kerja
        if (tanggalMasukKerja.isBefore(now.minusMonths(2)) || tanggalMasukKerja.isAfter(now.plusMonths(3))) {
            attributes.addAttribute("error", "Tanggal Masuk Kerja tidak valid");
            return "redirect:/edit/" + karyawan.getKodeKaryawan();
        }

        // Validasi nama karyawan
        try {
            karyawanDAO.updateKaryawan(karyawan);
        } catch (DuplicateNameException e) {
            attributes.addAttribute("error", "Nama karyawan sudah digunakan");
            return "redirect:/edit/" + karyawan.getKodeKaryawan();
        }
        return "redirect:/";
    }

    // delete data
    @PostMapping("/delete/{kodeKaryawan}")
    public String deleteKaryawan(@PathVariable("kodeKaryawan") String kodeKaryawan) {
        karyawanDAO.deleteKaryawan(kodeKaryawan);
        return "redirect:/";
    }

    // // search
    // @GetMapping("/search")
    // public String search(@RequestParam("namaKaryawan") String namaKaryawan,
    // @RequestParam("noHp") String noHp,
    // @RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate
    // startDate,
    // @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate
    // endDate,
    // Model model) {
    // List<Karyawan> karyawanList = karyawanDAO.search(namaKaryawan, noHp,
    // startDate, endDate);
    // model.addAttribute("karyawanList", karyawanList);
    // model.addAttribute("startDate",
    // startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    // model.addAttribute("endDate",
    // endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    // return "index";
    // }
    // search
    @GetMapping("/search")
    public String search(@RequestParam(name = "namaKaryawan", required = false) String namaKaryawan,
            @RequestParam(name = "noHp", required = false) String noHp,
            @RequestParam(name = "start_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "end_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Model model) {

        // Jika semua parameter kosong, tampilkan semua data karyawan
        if (namaKaryawan == null && noHp == null && startDate == null && endDate == null) {
            List<Karyawan> karyawanList = karyawanDAO.findAll();
            model.addAttribute("karyawanList", karyawanList);
            return "index";
        }

        // Validasi untuk parameter tanggal
        if (startDate == null) {
            startDate = LocalDate.of(1900, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        // Validasi untuk parameter nama karyawan dan no hp
        if (namaKaryawan == null) {
            namaKaryawan = "";
        }
        if (noHp == null) {
            noHp = "";
        }

        List<Karyawan> karyawanList = karyawanDAO.search(namaKaryawan, noHp, startDate, endDate);
        model.addAttribute("karyawanList", karyawanList);
        model.addAttribute("startDate", startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        model.addAttribute("endDate", endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return "index";
    }

}
