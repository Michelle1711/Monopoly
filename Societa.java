public class Societa extends Casella {
//commento per push
    private boolean acquistato;
    private int rendita;
    private int valoreAcquisto;
    private int valoreIpoteca;
    private Giocatore proprietario;

    public Societa(String nome, Casella successiva, Casella precedente, int numeroCasella) {
        super(nome, successiva, precedente, numeroCasella);
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

    public String toString(){
        return  super.toString()+ "valoreAcquisto=" + valoreAcquisto;
    }
}
