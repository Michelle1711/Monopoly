public class Terreno extends Casella{
    private int valoreAcquisto;
    private int nCase;
    private Colore colore;
    private int valoreIpoteca;
    private boolean ipotecato;
    //protected Giocatore proprietario;

    public int getValoreAcquisto() {
        return valoreAcquisto;
    }

    public int getnCase() {
        return nCase;
    }

    public Colore getColore() {
        return colore;
    }

    public int getValoreIpoteca() {
        return valoreIpoteca;
    }

    public boolean isIpotecato() {
        return ipotecato;
    }

    /*public Giocatore getProprietario(){
        return proprietario;
    }*/

    public Terreno(String nome, Casella successiva, Casella precedente, int numeroCasella, int valoreAcquisto, Colore colore) {
        super(nome, successiva, precedente, numeroCasella);
        this.valoreAcquisto = valoreAcquisto;
        this.colore = colore;
        nCase = 0;
        ipotecato = false;
        valoreIpoteca = valoreAcquisto / 2;
    }


}
