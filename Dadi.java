import java.util.Random;
//commento per push
public class Dadi {

    private int valore1;
    private int valore2;

    public Dadi() {
        valore1 = 0;
        valore2 = 0;
    }

    // Lancia i dadi
    public void lancia() {
        Random r = new Random();
        valore1 = r.nextInt(6) + 1;
        valore2 = r.nextInt(6) + 1;
    }

    public int getValoreDado() {
        lancia();
        return valore1 + valore2;
    }

    public boolean isDoppio() {
        return valore1 == valore2;
    }

    @Override
    public String toString() {
        return "Dadi: " + valore1 + " - " + valore2;
    }
}
