package parkirunila;

/**
 * Subclass Kendaraan untuk kendaraan roda empat.
 */
public class Mobil extends Kendaraan {

    private static final long serialVersionUID = 1L;

    public Mobil(String platNomor) {
        super(platNomor);
    }

    @Override
    public String getJenis() {
        return MOBIL;
    }

    @Override
    public String getInfoKendaraan() {
        return "Mobil (roda 4) - " + getPlatNomor() + " | Area: Parkir Mobil";
    }
}
