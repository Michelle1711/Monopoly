public class StazioneTreno {

    private boolean acquistato;
    private int rendita;
    private int valoreAcquisto;
    private int valoreIpoteca;
    private Giocatore proprietario;

    public StazioneTreno() {
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

