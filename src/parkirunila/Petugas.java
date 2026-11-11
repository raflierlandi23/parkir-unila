package parkirunila;

/**
 * Subclass Pengguna (opsional): petugas yang mencatat kendaraan tamu.
 */
public class Petugas extends Pengguna {

    private static final long serialVersionUID = 1L;

    public Petugas(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Petugas Parkir";
    }

    @Override
    protected void tampilkanMenu() {
        System.out.println("1. Catat kendaraan masuk (tamu)");
        System.out.println("2. Catat kendaraan keluar");
        System.out.println("3. Kendaraan sedang parkir");
        System.out.println("4. Ketersediaan slot");
    }

    @Override
    protected void prosesPilihan(String pilihan, Sistem sistem) {
        switch (pilihan) {
            case "1":
                catatMasuk(sistem);
                break;
            case "2":
                catatKeluar(sistem);
                break;
            case "3":
                sistem.getParkir().tampilkanSedangParkir();
                break;
            case "4":
                sistem.getParkir().tampilkanSlot();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private void catatMasuk(Sistem sistem) {
        Kendaraan k = inputKendaraan();
        if (k == null) {
            return;
        }
        String lokasi = pilihLokasi();
        if (lokasi == null) {
            return;
        }
        String error = sistem.getParkir().masuk(k, "Tamu", lokasi);
        System.out.println(error == null ? "Kendaraan masuk tercatat di " + lokasi + "." : error);
    }

    private void catatKeluar(Sistem sistem) {
        CatatanParkir c = sistem.getParkir().keluar(Input.teks("Plat nomor: "));
        if (c == null) {
            System.out.println("Kendaraan tidak ditemukan atau tidak sedang parkir.");
        } else {
            System.out.println("Kendaraan keluar tercatat. Durasi: " + c.getDurasi());
        }
    }
}
