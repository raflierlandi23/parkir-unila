package parkirunila;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Satu catatan parkir: kendaraan, pemilik, lokasi, jam masuk dan jam keluar.
 */
public class CatatanParkir implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final Kendaraan kendaraan;
    private final String pemilik;
    private final String lokasi;
    private final LocalDateTime jamMasuk;
    private LocalDateTime jamKeluar;

    public CatatanParkir(Kendaraan kendaraan, String pemilik, String lokasi) {
        this.kendaraan = kendaraan;
        this.pemilik = pemilik;
        this.lokasi = lokasi;
        this.jamMasuk = LocalDateTime.now();
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public String getPemilik() {
        return pemilik;
    }

    public String getLokasi() {
        return lokasi;
    }

    public LocalDateTime getJamMasuk() {
        return jamMasuk;
    }

    public LocalDateTime getJamKeluar() {
        return jamKeluar;
    }

    public boolean masihParkir() {
        return jamKeluar == null;
    }

    public void catatKeluar() {
        this.jamKeluar = LocalDateTime.now();
    }

    public String getDurasi() {
        LocalDateTime akhir = masihParkir() ? LocalDateTime.now() : jamKeluar;
        Duration d = Duration.between(jamMasuk, akhir);
        return d.toHours() + " jam " + (d.toMinutes() % 60) + " menit "
                + (d.getSeconds() % 60) + " detik";
    }

    @Override
    public String toString() {
        String keluar = masihParkir() ? "-" : jamKeluar.format(FORMAT);
        return kendaraan.getPlatNomor() + " (" + kendaraan.getJenis() + ") | Pemilik: " + pemilik
                + " | Lokasi: " + lokasi
                + "\n    Masuk: " + jamMasuk.format(FORMAT)
                + " | Keluar: " + keluar
                + " | Durasi: " + getDurasi();
    }
}
