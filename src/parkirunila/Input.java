package parkirunila;

import java.util.Scanner;

/**
 * Utilitas pembacaan input keyboard menggunakan Scanner.
 */
public class Input {

    private static final Scanner SC = new Scanner(System.in);

    private Input() {
        // kelas utilitas, tidak untuk diinstansiasi
    }

    /** Membaca satu baris teks dari keyboard. */
    public static String teks(String prompt) {
        System.out.print(prompt);
        return SC.nextLine().trim();
    }

    /** Membaca angka. Mengembalikan -1 jika input bukan angka. */
    public static int angka(String prompt) {
        try {
            return Integer.parseInt(teks(prompt));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
