class Giocatore{
    private String pedina;
    private int denaro;
    private Terreno[] terreni;
    private Casella casella;
    private int nDoppio=0;
    private int numCaselle=0;
    //costruttore, toString

    // bozza costruttore fatta da zancaner
    public Giocatore(String pedina, int denaro, Terreno[] terreni, Casella casella) {
        this.pedina = pedina;
        this.denaro = denaro;
        this.terreni = terreni;
        this.casella = casella;
    }

    public boolean isPrigione(){
        if(casella instanceof Prigione){
            return true;
        }else{
            return false;
        }
    }

    public boolean giroCompleto(){
        if (numCaselle==40){
            numCaselle=0;
            denaro+=200;
            return true;
        }else{
            return false;
        }
    }

    public void incrementoDenaro(int soldi){
        denaro+=soldi;
    }

    public boolean treDoppi(){
        return nDoppio==3;
    }

    public void muovi(Dadi dado){
        int valore=dado.getValoreDado();
        if(dado.isDoppio()){
            nDoppio++;
        }else{
            nDoppio=0;
        }
        while(valore!=0){
            Casella csuc=casella.getSuccessiva();
            casella=csuc;
            valore--;
        }
    }

    public boolean pagaAffitto(Giocatore g,int affitto){
        if(affitto<denaro){
            denaro-=affitto;
            g.riceviAffitto(affitto);
            return true;
        }else{
            return false;
        }
    }

    public void riceviAffitto(int affitto){
        denaro+=affitto;
    }
    public boolean acquistaTerreno(Terreno t){
        if(denaro>=t.getValoreAcquisto()){
            denaro-=t.getValoreAcquisto();
            //logica per aggiungere terreno all'array terreni
            return true;
        }else{
            return false;
        }
    }

    public String getPedina(){
        return pedina;
    }

    public boolean tentaUscitaPrigione(Dadi dado){
        if(dado.isDoppio()){
            return true;
        }else{
            return false;
        }
    }

    public boolean setCompleto(){
        int proprietaM=0;
        int proprietaA=0;
        int proprietaR=0;
        int proprietaAR=0;
        int proprietaG=0;
        int proprietaV=0;
        int proprietaB=0;
        for(int i=0;i<terreni.length;i++){
            if(terreni[i]!=null){
                switch(terreni[i].getColore()){
                    case MARRONE:
                        proprietaM++;
                        break;
                    case AZZURRO:
                        proprietaA++;
                        break;
                    case ROSA:
                        proprietaR++;
                        break;
                    case ARANCIONE:
                        proprietaAR++;
                        break;
                    case GIALLO:
                        proprietaG++;
                        break;
                    case VERDE:
                        proprietaV++;
                        break;
                    case BLU:
                        proprietaB++;
                        break;
                }
            }
        }
        return (proprietaM==2 || proprietaA==3 || proprietaR==3 || proprietaAR==3 || proprietaG==3 || proprietaV==3 || proprietaB==2);
    }
    
    public int pagaTassa(int tassa){
        if(tassa<denaro){
            denaro-=tassa;
            return tassa;
        }else{
            int temp=denaro;
            denaro=0;
            return temp;
        }
    }

    public void ipoteca(){

    }

    public String toString(){
        return "Giocatore: " + pedina + ", Denaro: " + denaro + ", Casella: " + casella.getNome();
    }
}