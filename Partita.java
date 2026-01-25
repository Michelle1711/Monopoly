public class Partita{
    private Giocatore listaGiocatori[];
    private Banca banca;
    private int indiceTurno;
    private Casella tabellone[];

    public Partita(Giocatore listaGiocatori[], Banca banca, Casella tabellone[]){
        this.listaGiocatori = listaGiocatori;
        this.banca = banca;
        this.tabellone = tabellone;
        this.indiceTurno = 0;
    }

    public void avviaPartita(){
        System.out.println("La partita è iniziata!");
        for(int i=0;i<listaGiocatori.length;i++){
            eseguiTurno(listaGiocatori[i]);
        }

    }

    public void prossimoTurno(){
        // Logica per gestire il turno del giocatore corrente
        indiceTurno = (indiceTurno + 1) % listaGiocatori.length;
    }

    public void eseguiTurno(Giocatore giocatore){
        giocatore.muovi(new Dadi());
        Casella casellaCorrente = giocatore.getCasella();
        if(casellaCorrente instanceof Terreno){
            System.out.println(giocatore.getNome() + " è atterrato su " + casellaCorrente.getNome());
            System.out.println("Vuoi acquistare questo terreno? (s/n)");
            String risposta = Leggi.unoString();
            if(risposta.equalsIgnoreCase("s")){
                Terreno terreno = (Terreno) casellaCorrente;
                if(giocatore.acquistaTerreno(terreno)){
                    System.out.println(giocatore.getNome() + " ha acquistato " + terreno.getNome());
                }else{
                    System.out.println(giocatore.getNome() + " non ha abbastanza denaro per acquistare " + terreno.getNome());
                }
            }
        } else if(casellaCorrente instanceof Tasse){
            Tasse tassa = (Tasse) casellaCorrente;
            System.out.println(giocatore.getNome() + " deve pagare una tassa di " + tassa.getValore());
            if(!giocatore.pagaTassa(tassa.getValore())){
                System.out.println(giocatore.getNome() + " non ha abbastanza denaro per pagare la tassa!");
                gestisciBancarotta(giocatore);
            }
        }else if(casellaCorrente instanceof Imprevisti){
            System.out.println(giocatore.getNome() + " ha pescato una carta Imprevisti.");
            Imprevisti imprevisti = (Imprevisti) casellaCorrente;
            int numero = (int) (Math.random() * 6);
            System.out.println(imprevisti.pescaCarta(numero));
            int soldi=imprevisti.getSoldi(numero);
            if(soldi<0){
                System.out.println(giocatore.getNome() + " deve pagare " + (-soldi) + "€.");
                if(!giocatore.pagaTassa(-soldi)){
                    System.out.println(giocatore.getNome() + " non ha abbastanza denaro per pagare!");
                    gestisciBancarotta(giocatore);
                }
                giocatore.pagaTassa(-soldi);
                banca.riceviDenaro(-soldi);
            }else if(soldi>0){
                System.out.println(giocatore.getNome() + " riceve " + soldi + "€.");
                giocatore.riceviAffitto(soldi);
            }

        }else if(casellaCorrente instanceof Probabilita){
            System.out.println(giocatore.getNome() + " ha pescato una carta Probabilità.");
            // Logica per gestire le carte Probabilità
        }else if(casellaCorrente instanceof Prigione){
            System.out.println(giocatore.getNome() + " è in prigione.");
            // Logica per gestire la prigione
        }else if(casellaCorrente instanceof StazioneTreno){
            System.out.println(giocatore.getNome() + " è atterrato su una Stazione Treno.");
            // Logica per gestire le stazioni treno
        }else if(casellaCorrente instanceof Casella){
            System.out.println(giocatore.getNome() + " è atterrato su " + casellaCorrente.getNome());
        }

    }

    public void gestisciBancarotta(Giocatore giocatore){
        // Logica per gestire la bancarotta di un giocatore
    }

    public void verificaVincitore(){
        // Logica per verificare se c'è un vincitore
    }

    public void applicaEffettoCasella(Giocatore giocatore, Casella casella){
        // Logica per applicare l'effetto della casella su cui il giocatore è atterrato
    }

    public void eseguiCicloTurno(){
        // Logica per eseguire un ciclo completo di turni
    }
}