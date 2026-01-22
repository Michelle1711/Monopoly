public class StazioneTreno extends Casella {
//commento per push
    private boolean acquistato;
    private int rendita;
    private int valoreAcquisto;
    private int valoreIpoteca;
    private Giocatore proprietario;

    public StazioneTreno(String nome, Casella successiva, Casella precedente, int numeroCasella) {
        super(nome, successiva, precedente, numeroCasella);
        acquistato = false;
        rendita = 25;
        valoreAcquisto = 200;
        valoreIpoteca = 100;
        proprietario = null;
    }

    public boolean isIpotecato() {
        return false;
    }

    public int valoreRendita() {
        return rendita;
    }

    public int getPrezzoAcquisto() {
        return valoreAcquisto;
    }
}

