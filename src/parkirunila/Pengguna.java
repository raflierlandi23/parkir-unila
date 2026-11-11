package parkirunila;

import java.io.Serializable;

/**
 * Superclass abstrak untuk semua pengguna sistem.
 * Subclass: Pengendara, Admin, Petugas.
 */
public abstract class Pengguna implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String username;
    private String password;

    public Pengguna(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean cekPassword(String pw) {
        return password.equals(pw);
    }

    public void setPassword(String passwordBaru) {
        if (passwordBaru != null && !passwordBaru.isEmpty()) {
            this.password = passwordBaru;
        }
    }

    public abstract String getRole();

    /** Keterangan singkat pengguna untuk daftar pengguna. */
    public String getInfo() {
        return getUsername() + " [" + getRole() + "]";
    }

    /** Menampilkan daftar menu khusus role (tanpa opsi logout). */
    protected abstract void tampilkanMenu();

    /** Memproses pilihan menu khusus role. */
    protected abstract void prosesPilihan(String pilihan, Sistem sistem);

    /** Loop menu yang sama untuk semua role (template method). */
    public void jalankanMenu(Sistem sistem) {
        boolean lanjut = true;
        while (lanjut) {
            System.out.println("\n=== MENU " + getRole().toUpperCase() + " (" + username + ") ===");
            tampilkanMenu();
            System.out.println("0. Logout");

            String pilihan = Input.teks("Pilih: ");
            if (pilihan.equals("0")) {
                lanjut = false;
            } else {
                prosesPilihan(pilihan, sistem);
                Penyimpanan.simpan(sistem); // data otomatis tersimpan ke file
            }
        }
    }

    /** Meminta jenis dan plat nomor. Mengembalikan null jika input tidak valid. */
    protected Kendaraan inputKendaraan() {
        String jenis = Input.teks("Jenis (1=Motor, 2=Mobil): ");
        if (!jenis.equals("1") && !jenis.equals("2")) {
            System.out.println("Jenis tidak valid.");
            return null;
        }
        String plat = Input.teks("Plat nomor (contoh BE 1234 AB): ");
        if (plat.isEmpty()) {
            System.out.println("Plat nomor tidak boleh kosong.");
            return null;
        }
        return jenis.equals("1") ? new Motor(plat) : new Mobil(plat);
    }

    /** Meminta pengguna memilih lokasi parkir. Mengembalikan null jika tidak valid. */
    protected String pilihLokasi() {
        System.out.println("Lokasi parkir:");
        for (int i = 0; i < Parkir.LOKASI.length; i++) {
            System.out.println((i + 1) + ". " + Parkir.LOKASI[i]);
        }
        int indeks = Input.angka("Pilih lokasi: ") - 1;
        if (indeks < 0 || indeks >= Parkir.LOKASI.length) {
            System.out.println("Lokasi tidak valid.");
            return null;
        }
        return Parkir.LOKASI[indeks];
    }
}
