package parkirunila;

import java.io.Serializable;

/**
 * Superclass abstrak untuk semua jenis kendaraan.
 */
public abstract class Kendaraan implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String MOTOR = "Motor";
    public static final String MOBIL = "Mobil";

    private String platNomor;

    public Kendaraan(String platNomor) {
        setPlatNomor(platNomor);
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public final void setPlatNomor(String platNomor) {
        this.platNomor = platNomor.trim().toUpperCase();
    }

    public abstract String getJenis();

    public abstract String getInfoKendaraan();
}
