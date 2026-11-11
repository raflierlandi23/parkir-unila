package parkirunila;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclass Pengguna: mahasiswa, dosen, atau tenaga kependidikan pemilik kendaraan.
 */
public class Pengendara extends Pengguna {

    private static final long serialVersionUID = 1L;

    public static final String[] STATUS = {"Mahasiswa", "Dosen", "Tenaga Kependidikan"};

    private String nama;
    private final String status;
    private final String nomorInduk;
    private final ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();

    public Pengendara(String username, String password, String nama, String status, String nomorInduk) {
        super(username, password);
        this.nama = nama;
        this.status = status;
        this.nomorInduk = nomorInduk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama != null && !nama.trim().isEmpty()) {
            this.nama = nama.trim();
        }
    }

    public String getStatus() {
        return status;
    }

    public String getNomorInduk() {
        return nomorInduk;
    }

    public List<Kendaraan> getDaftarKendaraan() {
        return daftarKendaraan;
    }

    @Override
    public String getRole() {
        return "Pengendara";
    }

    @Override
    public String getInfo() {
        return getUsername() + " [Pengendara] " + nama + " - " + status + " (" + nomorInduk + ")";
    }

    @Override
    protected void tampilkanMenu() {
        System.out.println("1. Daftarkan kendaraan");
        System.out.println("2. Parkir masuk");
        System.out.println("3. Parkir keluar");
        System.out.println("4. Lihat ketersediaan slot");
        System.out.println("5. Riwayat parkir saya");
        System.out.println("6. Kendaraan saya");
        System.out.println("7. Ubah profil (nama / password)");
    }

    @Override
    protected void prosesPilihan(String pilihan, Sistem sistem) {
        switch (pilihan) {
            case "1":
                daftarkanKendaraan(sistem);
                break;
            case "2":
                parkirMasuk(sistem);
                break;
            case "3":
                parkirKeluar(sistem);
                break;
            case "4":
                sistem.getParkir().tampilkanSlot();
                break;
            case "5":
                sistem.getParkir().tampilkanRiwayat(getUsername());
                break;
            case "6":
                tampilkanKendaraanSaya();
                break;
            case "7":
                ubahProfil();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private void daftarkanKendaraan(Sistem sistem) {
        Kendaraan k = inputKendaraan();
        if (k == null) {
            return;
        }
        if (sistem.platTerdaftar(k.getPlatNomor())) {
            System.out.println("Plat nomor " + k.getPlatNomor() + " sudah terdaftar.");
        } else {
            daftarKendaraan.add(k);
            System.out.println("Kendaraan berhasil didaftarkan.");
        }
    }

    private void parkirMasuk(Sistem sistem) {
        Kendaraan k = pilihKendaraan();
        if (k == null) {
            return;
        }
        String lokasi = pilihLokasi();
        if (lokasi == null) {
            return;
        }
        String error = sistem.getParkir().masuk(k, getUsername(), lokasi);
        System.out.println(error == null ? "Parkir masuk tercatat di " + lokasi + "." : error);
    }

    private void parkirKeluar(Sistem sistem) {
        Kendaraan k = pilihKendaraan();
        if (k == null) {
            return;
        }
        CatatanParkir c = sistem.getParkir().keluar(k.getPlatNomor());
        if (c == null) {
            System.out.println("Kendaraan ini tidak sedang parkir.");
        } else {
            System.out.println("Parkir keluar tercatat.");
            System.out.println("Durasi parkir: " + c.getDurasi() + " (gratis)");
        }
    }

    private void tampilkanKendaraanSaya() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("(belum ada kendaraan terdaftar)");
            return;
        }
        for (Kendaraan k : daftarKendaraan) {
            System.out.println("- " + k.getInfoKendaraan());
        }
    }

    private void ubahProfil() {
        System.out.println("Nama saat ini: " + nama);
        String namaBaru = Input.teks("Nama baru (kosongkan jika tidak diubah): ");
        if (!namaBaru.isEmpty()) {
            setNama(namaBaru);
            System.out.println("Nama diperbarui menjadi " + getNama() + ".");
        }

        String lama = Input.teks("Password lama (kosongkan jika tidak ganti password): ");
        if (!lama.isEmpty()) {
            if (!cekPassword(lama)) {
                System.out.println("Password lama salah.");
                return;
            }
            String baru = Input.teks("Password baru: ");
            if (baru.isEmpty()) {
                System.out.println("Password baru tidak boleh kosong.");
            } else {
                setPassword(baru);
                System.out.println("Password diperbarui.");
            }
        }
    }

    private Kendaraan pilihKendaraan() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada kendaraan terdaftar. Daftarkan dulu (menu 1).");
            return null;
        }
        for (int i = 0; i < daftarKendaraan.size(); i++) {
            System.out.println((i + 1) + ". " + daftarKendaraan.get(i).getInfoKendaraan());
        }
        int indeks = Input.angka("Pilih kendaraan: ") - 1;
        if (indeks < 0 || indeks >= daftarKendaraan.size()) {
            System.out.println("Pilihan tidak valid.");
            return null;
        }
        return daftarKendaraan.get(indeks);
    }
}
