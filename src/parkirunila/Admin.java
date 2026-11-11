package parkirunila;

/**
 * Subclass Pengguna: pengelola sistem parkir.
 */
public class Admin extends Pengguna {

    private static final long serialVersionUID = 1L;

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    protected void tampilkanMenu() {
        System.out.println("1. Daftar pengguna");
        System.out.println("2. Daftar kendaraan terdaftar");
        System.out.println("3. Kendaraan sedang parkir");
        System.out.println("4. Seluruh riwayat parkir");
        System.out.println("5. Ketersediaan slot");
        System.out.println("6. Ubah kapasitas slot");
        System.out.println("7. Hapus pengguna");
    }

    @Override
    protected void prosesPilihan(String pilihan, Sistem sistem) {
        switch (pilihan) {
            case "1":
                tampilkanPengguna(sistem);
                break;
            case "2":
                tampilkanKendaraanTerdaftar(sistem);
                break;
            case "3":
                sistem.getParkir().tampilkanSedangParkir();
                break;
            case "4":
                sistem.getParkir().tampilkanRiwayat(null);
                break;
            case "5":
                sistem.getParkir().tampilkanSlot();
                break;
            case "6":
                ubahKapasitas(sistem);
                break;
            case "7":
                hapusPengguna(sistem);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private void tampilkanPengguna(Sistem sistem) {
        for (Pengguna p : sistem.getDaftarPengguna()) {
            System.out.println("- " + p.getInfo());
        }
    }

    private void tampilkanKendaraanTerdaftar(Sistem sistem) {
        boolean ada = false;
        for (Pengguna p : sistem.getDaftarPengguna()) {
            if (p instanceof Pengendara) {
                for (Kendaraan k : ((Pengendara) p).getDaftarKendaraan()) {
                    System.out.println("- " + k.getInfoKendaraan() + " | Pemilik: " + p.getUsername());
                    ada = true;
                }
            }
        }
        if (!ada) {
            System.out.println("(belum ada kendaraan terdaftar)");
        }
    }

    private void ubahKapasitas(Sistem sistem) {
        int motor = Input.angka("Kapasitas Motor baru: ");
        int mobil = Input.angka("Kapasitas Mobil baru: ");
        Parkir parkir = sistem.getParkir();
        if (motor < 0 || mobil < 0) {
            System.out.println("Input harus berupa angka positif.");
        } else if (motor < parkir.hitungTerisi(Kendaraan.MOTOR)
                || mobil < parkir.hitungTerisi(Kendaraan.MOBIL)) {
            System.out.println("Kapasitas tidak boleh lebih kecil dari jumlah kendaraan yang sedang parkir.");
        } else {
            parkir.setKapasitas(motor, mobil);
            System.out.println("Kapasitas diperbarui.");
        }
    }

    private void hapusPengguna(Sistem sistem) {
        Pengguna p = sistem.cariPengguna(Input.teks("Username yang dihapus: "));
        if (p == null) {
            System.out.println("Pengguna tidak ditemukan.");
        } else if (p instanceof Admin) {
            System.out.println("Akun admin tidak dapat dihapus.");
        } else {
            sistem.getDaftarPengguna().remove(p);
            System.out.println("Pengguna dihapus.");
        }
    }
}
