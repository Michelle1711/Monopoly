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
        int rendita = 0;
        if(ipotecato){
            return 0;
        }
        if(nCase == 0){
            switch(colore){
                case MARRONE:
                    rendita = 2;
                    break;
                case AZZURRO:
                    rendita = 6;
                    break;
                case ROSA:
                    rendita = 10;
                    break;
                case ARANCIONE:
                    rendita = 16;
                    break;
                case ROSSO:
                    rendita = 18;
                    break;
                case GIALLO:
                    rendita = 22;
                    break;
                case VERDE:
                    rendita = 27;
                    break;
                default: // BLU
                    rendita = 35;
            }
        } else if(nCase == 1){
            switch(colore){
                case MARRONE:
                    rendita = 10;
                    break;
                case AZZURRO:
                    rendita = 20;
                    break;
                case ROSA:
                    rendita = 30;
                    break;
                case ARANCIONE:
                    rendita = 35;
                    break;
                case ROSSO:
                    rendita = 40;
                    break;
                case GIALLO:
                    rendita = 45;
                    break;
                case VERDE:
                    rendita = 50;
                    break;
                default: // BLU
                    rendita = 150;
            }
        } else if (nCase == 2){
            switch(colore){
                case MARRONE:
                    rendita = 15;
                    break;
                case AZZURRO:
                    rendita = 30;
                    break;
                case ROSA:
                    rendita = 50;
                    break;
                case ARANCIONE:
                    rendita = 80;
                    break;
                case ROSSO:
                    rendita = 90;
                    break;
                case GIALLO:
                    rendita = 100;
                    break;
                case VERDE:
                    rendita = 150;
                    break;
                default: // BLU
                    rendita = 200;
            }
        } else if (nCase == 3){
            switch(colore){
                case MARRONE:
                    rendita = 90;
                    break;
                case AZZURRO:
                    rendita = 120;
                    break;
                case ROSA:
                    rendita = 170;
                    break;
                case ARANCIONE:
                    rendita = 200;
                    break;
                case ROSSO:
                    rendita = 270;
                    break;
                case GIALLO:
                    rendita = 300;
                    break;
                case VERDE:
                    rendita = 400;
                    break;
                default: // BLU
                    rendita = 450;
            }
        } else if (nCase == 4){
            switch(colore){
                case MARRONE:
                    rendita = 140;
                    break;
                case AZZURRO:
                    rendita = 200;
                    break;
                case ROSA:
                    rendita = 270;
                    break;
                case ARANCIONE:
                    rendita = 320;
                    break;
                case ROSSO:
                    rendita = 400;
                    break;
                case GIALLO:
                    rendita = 450;
                    break;
                case VERDE:
                    rendita = 650;
                    break;
                default: // BLU
                    rendita = 900;
            }
        } else if (nCase == 5){
            switch(colore){
                case MARRONE:
                    rendita = 250;
                    break;
                case AZZURRO:
                    rendita = 450;
                    break;
                case ROSA:
                    rendita = 550;
                    break;
                case ARANCIONE:
                    rendita = 700;
                    break;
                case ROSSO:
                    rendita = 850;
                    break;
                case GIALLO:
                    rendita = 1000;
                    break;
                case VERDE:
                    rendita = 1250;
                    break;
                default: //BLU
                    rendita = 1750;
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

    public String toString(){
        return  super.toString()+ "valoreAcquisto=" + valoreAcquisto;
    }
}
