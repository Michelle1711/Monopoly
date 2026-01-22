public class Terreno extends Casella{
    private int valoreAcquisto;
    private int nCase;
    private Colore colore;
    private int valoreIpoteca;
    private boolean ipotecato;
    private Giocatore proprietario;

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

    public Giocatore getProprietario(){
        return proprietario;
    }

    public Terreno(String nome, Casella successiva, Casella precedente, int numeroCasella, int valoreAcquisto, Colore colore, Giocatore proprietario) {
        super(nome, successiva, precedente, numeroCasella);
        this.valoreAcquisto = valoreAcquisto;
        this.colore = colore;
        nCase = 0;
        ipotecato = false;
        valoreIpoteca = valoreAcquisto / 2;
        this.proprietario = proprietario;
    }

    public void costruisciCasa(){
        if(nCase >= 5){
            return;
        } else{
            nCase++;
        }
    }

    public int valoreRendita(){
        int rendita;
        if(nCase == 1){
            if(colore == Colore.MARRONE || colore == Colore.AZZURRO){

            } else if(colore == Colore.ROSA || colore == Colore.ARANCIONE){

            } else if(colore == Colore.ROSSO || colore == Colore.GIALLO){

            } else{

            }
        } else if (nCase == 2){
            if(colore == Colore.MARRONE || colore == Colore.AZZURRO){

            } else if(colore == Colore.ROSA || colore == Colore.ARANCIONE){

            } else if(colore == Colore.ROSSO || colore == Colore.GIALLO){

            } else{

            }
        } else if (nCase == 3){
            if(colore == Colore.MARRONE || colore == Colore.AZZURRO){

            } else if(colore == Colore.ROSA || colore == Colore.ARANCIONE){

            } else if(colore == Colore.ROSSO || colore == Colore.GIALLO){

            } else{

            }
        } else if (nCase == 4){
            if(colore == Colore.MARRONE || colore == Colore.AZZURRO){

            } else if(colore == Colore.ROSA || colore == Colore.ARANCIONE){

            } else if(colore == Colore.ROSSO || colore == Colore.GIALLO){

            } else{

            }
        } else if (nCase == 5){
            if(colore == Colore.MARRONE || colore == Colore.AZZURRO){

            } else if(colore == Colore.ROSA || colore == Colore.ARANCIONE){

            } else if(colore == Colore.ROSSO || colore == Colore.GIALLO){

            } else{

            }
        }
        return rendita;
    }

    public int numeroCase(){
        return nCase;
    }

    public int getPrezzoAcquisto(){
        return valoreAcquisto;
    }

    public int getCostoCasa(){
        int costoCasa;
        if(colore == Colore.MARRONE || colore == Colore.AZZURRO){
            costoCasa = 50;
        } else if(colore == Colore.ROSA || colore == Colore.ARANCIONE){
            costoCasa = 100;
        } else if(colore == Colore.ROSSO || colore == Colore.GIALLO){
            costoCasa = 150;
        } else{
            costoCasa = 200;
        }
        return costoCasa;
    }




}
