package parkirunila;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Pengelola kapasitas slot dan seluruh catatan parkir.
 */
public class Parkir implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Lokasi parkir di lingkungan kampus UNILA (dapat disesuaikan). */
    public static final String[] LOKASI = {
        "Rektorat",
        "Gedung Serba Guna (GSG)",
        "Perpustakaan Pusat",
        "Fakultas Ekonomi dan Bisnis",
        "Fakultas Teknik",
        "FMIPA",
        "Fakultas Hukum",
        "FISIP",
        "FKIP",
        "Fakultas Pertanian",
        "Fakultas Kedokteran"
    };

    private int kapasitasMotor;
    private int kapasitasMobil;
    private final ArrayList<CatatanParkir> semuaCatatan = new ArrayList<>();

    public Parkir(int kapasitasMotor, int kapasitasMobil) {
        this.kapasitasMotor = kapasitasMotor;
        this.kapasitasMobil = kapasitasMobil;
    }

    public void setKapasitas(int motor, int mobil) {
        this.kapasitasMotor = motor;
        this.kapasitasMobil = mobil;
    }

    public int getKapasitas(String jenis) {
        return jenis.equals(Kendaraan.MOTOR) ? kapasitasMotor : kapasitasMobil;
    }

    public int hitungTerisi(String jenis) {
        int jumlah = 0;
        for (CatatanParkir c : semuaCatatan) {
            if (c.masihParkir() && c.getKendaraan().getJenis().equals(jenis)) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public int sisaSlot(String jenis) {
        return getKapasitas(jenis) - hitungTerisi(jenis);
    }

    private CatatanParkir cariAktif(String plat) {
        for (CatatanParkir c : semuaCatatan) {
            if (c.masihParkir() && c.getKendaraan().getPlatNomor().equalsIgnoreCase(plat.trim())) {
                return c;
            }
        }
        return null;
    }

    /** Mengembalikan null jika berhasil, atau pesan error jika gagal. */
    public String masuk(Kendaraan k, String pemilik, String lokasi) {
        if (cariAktif(k.getPlatNomor()) != null) {
            return "Kendaraan " + k.getPlatNomor() + " sudah tercatat sedang parkir.";
        }
        if (sisaSlot(k.getJenis()) <= 0) {
            return "Slot parkir " + k.getJenis() + " penuh!";
        }
        semuaCatatan.add(new CatatanParkir(k, pemilik, lokasi));
        return null;
    }

    /** Mengembalikan catatan yang ditutup, atau null jika kendaraan tidak sedang parkir. */
    public CatatanParkir keluar(String plat) {
        CatatanParkir c = cariAktif(plat);
        if (c != null) {
            c.catatKeluar();
        }
        return c;
    }

    public void tampilkanSlot() {
        System.out.println("--- Ketersediaan Slot Parkir UNILA ---");
        System.out.println("Motor : " + sisaSlot(Kendaraan.MOTOR) + " tersedia dari " + kapasitasMotor);
        System.out.println("Mobil : " + sisaSlot(Kendaraan.MOBIL) + " tersedia dari " + kapasitasMobil);
    }

    public void tampilkanSedangParkir() {
        System.out.println("--- Kendaraan Sedang Parkir ---");
        boolean ada = false;
        for (CatatanParkir c : semuaCatatan) {
            if (c.masihParkir()) {
                System.out.println("- " + c);
                ada = true;
            }
        }
        if (!ada) {
            System.out.println("(kosong)");
        }
    }

    /** Parameter pemilik bernilai null untuk menampilkan seluruh riwayat. */
    public void tampilkanRiwayat(String pemilik) {
        System.out.println("--- Riwayat Parkir ---");
        boolean ada = false;
        for (CatatanParkir c : semuaCatatan) {
            if (pemilik == null || c.getPemilik().equals(pemilik)) {
                System.out.println("- " + c);
                ada = true;
            }
        }
        if (!ada) {
            System.out.println("(belum ada riwayat)");
        }
    }
}
