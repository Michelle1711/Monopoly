public class Tasse extends Casella{
    private int valore;

    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public Tasse(String nome, Casella successiva, Casella precedente, int numeroCasella, int valore) {
        super(nome, successiva, precedente, numeroCasella);
        this.valore = valore;
    }


}
