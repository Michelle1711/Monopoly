class Giocatore{
    private String pedina;
    private int denaro;
    private Terreno[] terreni;
    private Casella casella;
    private Casella prigione;
    private int nDoppio;
    private int numCaselle=0;
    //costruttore, toString

    // bozza costruttore fatta da zancaner
    public Giocatore(String pedina, int denaro, Terreno[] terreni, Casella casella, int nDoppio, int numCaselle) {
        this.pedina = pedina;
        this.denaro = denaro;
        this.terreni = terreni;
        this.casella = casella;
        this.nDoppio = nDoppio;
        this.numCaselle = numCaselle;
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
        int valore=dado.valoreDadi();
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
    //acquistaTerreno

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

    
}