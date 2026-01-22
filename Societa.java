public class Societa {

    private boolean acquistato;
    private int rendita;
    private int valoreAcquisto;
    private int valoreIpoteca;
    private Giocatore proprietario;

    public Societa() {
        acquistato = false;
        rendita = 50;
        valoreAcquisto = 150;
        valoreIpoteca = 75;
        proprietario = null;
    }

    public boolean isIpotecato() {
        return false;
    }

    public int getPrezzoAcquisto() {
        return valoreAcquisto;
    }

    public int affitto() {
        return rendita;
    }
}
