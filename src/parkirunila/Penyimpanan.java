package parkirunila;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Menyimpan dan memuat data aplikasi ke file menggunakan Object Serialization
 * (materi File I/O, Stream, Serialization, dan Deserialization).
 */
public class Penyimpanan {

    private static final String NAMA_FILE = "data_parkir.dat";

    private Penyimpanan() {
        // kelas utilitas, tidak untuk diinstansiasi
    }

    /** Serialization: membekukan objek Sistem ke dalam file. */
    public static void simpan(Sistem sistem) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(NAMA_FILE))) {
            out.writeObject(sistem);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    /** Deserialization: memuat objek Sistem dari file. Mengembalikan null jika belum ada atau gagal. */
    public static Sistem muat() {
        File file = new File(NAMA_FILE);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Sistem) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Gagal memuat data: " + e.getMessage());
            return null;
        }
    }
}
