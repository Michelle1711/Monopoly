class Giocatore{
    private String pedina;
    private int denaro;
    private Terreni[] terreni;
    private Casella casella;
    private int nCasella;
    private int nDoppio;
    private int numCaselle=0;

    //costruttore, toString

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
            Casella csuc=casella.getSucessivo();
            casella=csuc;
            valore--;
        }
    }

    public int pagaAffitto(Giocatore g,int affitto){
        if(affitto<denaro){
            denaro-=affitto;
            g.riceviAffitto(affitto);
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