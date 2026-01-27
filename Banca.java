public class Banca {
    private int soldiGioco;
    private Casella[] caselleInVendita;

    public Banca(Casella[] caselleInVendita) {
        soldiGioco = 25000;
        this.caselleInVendita = caselleInVendita;
    }

    public int getSoldiGioco() {
        return soldiGioco;
    }

    public void setSoldiGioco(int soldiGioco) {
        this.soldiGioco = soldiGioco;
    }

    public Casella[] getCaselleInVendita() {
        return caselleInVendita;
    }

    public void setCaselleInVendita(Casella[] caselleInVendita) {
        this.caselleInVendita = caselleInVendita;
    }
}
