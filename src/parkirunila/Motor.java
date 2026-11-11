package parkirunila;

/**
 * Subclass Kendaraan untuk kendaraan roda dua.
 */
public class Motor extends Kendaraan {

    private static final long serialVersionUID = 1L;

    public Motor(String platNomor) {
        super(platNomor);
    }

    @Override
    public String getJenis() {
        return MOTOR;
    }

    @Override
    public String getInfoKendaraan() {
        return "Motor (roda 2) - " + getPlatNomor() + " | Area: Parkir Motor";
    }
}
