public class Banca {
    private int soldiGioco;
    private Casella[] caselleInVendita;

    public Banca(Casella[] caselleInVendita) {
        soldiGioco = 25000;
        this.caselleInVendita = caselleInVendita;
    }

    public int getBudgetGioco() {
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

    public void incremento(int n){
        soldiGioco += n;
    }

    public void decremento(int n){
        soldiGioco -= n;
    }

    public boolean vendi(Casella casella, Giocatore acquirente) {
        if (casella instanceof Terreno) {
            Terreno terreno = (Terreno) casella;
            if (terreno.isIpotecato()) {
                return false;
            }
            if (terreno.getnCase() > 0) {
                return false;
            }
            if (acquirente.getBudget() >= terreno.getValoreAcquisto()) {
                acquirente.pagaTassa(terreno.getValoreAcquisto());
                incremento(terreno.getValoreAcquisto());
                terreno.setProprietario(acquirente);
            } else {
                return false;
            }
        } else if (casella instanceof Societa) {
            Societa societa = (Societa) casella;
            if (societa.getProprietario() != null) {
                return false;
            }
            if (acquirente.getBudget() >= societa.getPrezzoAcquisto()) {
                acquirente.pagaTassa(societa.getPrezzoAcquisto());
                incremento(societa.getPrezzoAcquisto());
                societa.setProprietario(acquirente);
            } else {
                return false;
            }
        } else if (casella instanceof StazioneTreno) {
            StazioneTreno stazione = (StazioneTreno) casella;
            if (stazione.getProprietario() != null) {
                return false;
            }
            if (acquirente.getBudget() >= stazione.getPrezzoAcquisto()) {
                acquirente.pagaTassa(stazione.getPrezzoAcquisto());
                incremento(stazione.getPrezzoAcquisto());
                stazione.setProprietario(acquirente);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public void mettiInVendta(Terreno t){

    }
}
