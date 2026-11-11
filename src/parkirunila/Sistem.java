package parkirunila;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Menyimpan seluruh data aplikasi: daftar pengguna dan pengelola parkir.
 */
public class Sistem implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ArrayList<Pengguna> daftarPengguna = new ArrayList<>();
    private final Parkir parkir = new Parkir(100, 30);

    public Sistem() {
        daftarPengguna.add(new Admin("admin", "admin123"));
        daftarPengguna.add(new Petugas("petugas", "petugas123"));
    }

    public List<Pengguna> getDaftarPengguna() {
        return daftarPengguna;
    }

    public Parkir getParkir() {
        return parkir;
    }

    public Pengguna cariPengguna(String username) {
        for (Pengguna p : daftarPengguna) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return p;
            }
        }
        return null;
    }

    public Pengguna login(String username, String password) {
        Pengguna p = cariPengguna(username);
        return (p != null && p.cekPassword(password)) ? p : null;
    }

    public boolean platTerdaftar(String plat) {
        for (Pengguna p : daftarPengguna) {
            if (p instanceof Pengendara) {
                for (Kendaraan k : ((Pengendara) p).getDaftarKendaraan()) {
                    if (k.getPlatNomor().equalsIgnoreCase(plat.trim())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
