package parkirunila;

/**
 * Sistem Manajemen Parkir Kampus Universitas Lampung (UNILA).
 * Pilar Smart City: Smart Mobility. Parkir gratis, fokus pada pencatatan
 * kendaraan dan pemantauan kapasitas slot.
 */
public class Main {

    public static void main(String[] args) {
        // Instansiasi objek: muat data dari file, atau buat objek Sistem baru
        Sistem sistem = Penyimpanan.muat();
        if (sistem == null) {
            sistem = new Sistem();
            System.out.println("Memulai dengan data baru.");
        } else {
            System.out.println("Data berhasil dimuat dari file.");
        }

        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenuUtama();
            String pilihan = Input.teks("Pilih: ");

            switch (pilihan) {
                case "1":
                    login(sistem);
                    break;
                case "2":
                    registrasi(sistem);
                    break;
                case "3":
                    sistem.getParkir().tampilkanSlot();
                    break;
                case "0":
                    Penyimpanan.simpan(sistem);
                    System.out.println("Data tersimpan. Terima kasih.");
                    berjalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tampilkanMenuUtama() {
        System.out.println("\n=============================================");
        System.out.println("  SISTEM MANAJEMEN PARKIR KAMPUS");
        System.out.println("  UNIVERSITAS LAMPUNG (UNILA)");
        System.out.println("=============================================");
        System.out.println("1. Login");
        System.out.println("2. Registrasi Pengendara");
        System.out.println("3. Lihat ketersediaan slot");
        System.out.println("0. Keluar");
    }

    private static void login(Sistem sistem) {
        String username = Input.teks("Username: ");
        String password = Input.teks("Password: ");
        Pengguna user = sistem.login(username, password);

        if (user == null) {
            System.out.println("Username atau password salah.");
        } else {
            System.out.println("Login berhasil sebagai " + user.getRole());
            user.jalankanMenu(sistem);
        }
    }

    private static void registrasi(Sistem sistem) {
        String nama = Input.teks("Nama lengkap: ");
        System.out.println("Status: 1. Mahasiswa  2. Dosen  3. Tenaga Kependidikan");
        int pilihStatus = Input.angka("Pilih status: ");
        if (pilihStatus < 1 || pilihStatus > Pengendara.STATUS.length) {
            System.out.println("Status tidak valid.");
            return;
        }
        String status = Pengendara.STATUS[pilihStatus - 1];
        String nomorInduk = Input.teks(pilihStatus == 1 ? "NPM: " : "NIP: ");
        String username = Input.teks("Username baru: ");
        String password = Input.teks("Password baru: ");

        if (nama.isEmpty() || nomorInduk.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("Semua data wajib diisi.");
        } else if (sistem.cariPengguna(username) != null) {
            System.out.println("Username sudah dipakai.");
        } else {
            sistem.getDaftarPengguna().add(new Pengendara(username, password, nama, status, nomorInduk));
            Penyimpanan.simpan(sistem);
            System.out.println("Registrasi berhasil. Silakan login.");
        }
    }
}
