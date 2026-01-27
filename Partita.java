public class Partita {
    private Giocatore[] listaGiocatori;
    private Banca banca;
    private int indiceTurno;
    private Tabellone tabellone; // Oggetto Tabellone per la stampa con pedine
    private Casella[] tabelloneArray; // Array di caselle per accedere direttamente
    private Dadi dadi; // I dadi è meglio crearli una volta sola
    private int contatoreTurni; // Contatore per evitare loop infinito

    public Partita(Giocatore[] listaGiocatori, Banca banca, Tabellone tabellone) {
        this.listaGiocatori = listaGiocatori;
        this.banca = banca;
        this.tabellone = tabellone;
        this.tabelloneArray = tabellone.getTabellone();
        this.indiceTurno = 0;
        this.dadi = new Dadi(); 
        this.contatoreTurni = 0;
    }

    public void avviaPartita() {
        System.out.println("--- MONOPOLY: La partita è iniziata! ---");
        tabellone.stampaTabelloneGioco(listaGiocatori); // Stampa il tabellone con le pedine
        
        // Ciclo principale del gioco
        while (!verificaVincitore()) {
            
            Giocatore giocatoreCorrente = listaGiocatori[indiceTurno];
            
            // Se il giocatore non è stato eliminato (non è null), gioca
            if (giocatoreCorrente != null) {
                contatoreTurni++;
                System.out.println("\n--------------------------------");
                System.out.println("Turno " + contatoreTurni + ": " + giocatoreCorrente.getNome() + " [Budget: " + giocatoreCorrente.getBudget() + "]");
                eseguiTurno(giocatoreCorrente);
            }
            
            // Passa al prossimo, saltando eventuali giocatori eliminati
            passaAlProssimoGiocatoreValido();
        }
        
        annunciaVincitore();
    }

    // Metodo helper per gestire i turni saltando i null
    private void passaAlProssimoGiocatoreValido() {
        do {
            indiceTurno = (indiceTurno + 1) % listaGiocatori.length;
        } while (listaGiocatori[indiceTurno] == null && !verificaVincitore()); 
        // Continua a girare finché non trova un giocatore vivo o finisce la partita
    }

    public void eseguiTurno(Giocatore giocatore) {
        if (giocatore.isInPrigione()) {
            if (giocatore.getTurniFermo() > 0) {
                System.out.println(giocatore.getNome() + " è bloccato in prigione per altri " + giocatore.getTurniFermo() + " turni.");
                giocatore.decrementaTurniFermo();
                return; // Salta il turno
            } else {
                System.out.println(giocatore.getNome() + " esce di prigione!");
                giocatore.setInPrigione(false);
            }
        }

        giocatore.muovi(dadi); 
        tabellone.stampaTabelloneGioco(listaGiocatori); // Aggiorna la stampa dopo il movimento
        Casella casellaCorrente = giocatore.getCasella();
        System.out.println("Posizione: " + casellaCorrente.getNome());

        // --- GESTIONE TIPI CASELLA (Logica instanceof) ---
        
        if (casellaCorrente instanceof Terreno) {
            gestisciTerreno(giocatore, (Terreno) casellaCorrente);
        } 
        else if (casellaCorrente instanceof StazioneTreno) {
            gestisciStazione(giocatore, (StazioneTreno) casellaCorrente);
        }
        else if (casellaCorrente instanceof Societa) {
            gestisciSocieta(giocatore, (Societa) casellaCorrente);
        }
        else if (casellaCorrente instanceof Tasse) {
            gestisciTasse(giocatore, (Tasse) casellaCorrente);
        }
        else if (casellaCorrente instanceof Imprevisti) {
            gestisciImprevisti(giocatore, (Imprevisti) casellaCorrente);
        }
        else if (casellaCorrente instanceof Probabilita) {
            gestisciProbabilita(giocatore, (Probabilita) casellaCorrente);
        }
        else if (casellaCorrente instanceof Prigione) {
            // Se è la casella "Vai in prigione" (indice 30)
            if (casellaCorrente.getNome().equalsIgnoreCase("VAI IN PRIGIONE!")) {
                System.out.println("ARRESTO! " + giocatore.getNome() + " viene spedito in prigione.");
                giocatore.setCasella(tabelloneArray[10]); // Sposta fisicamente al transito
                giocatore.setInPrigione(true);
                giocatore.setTurniFermo(3);
            } else {
                System.out.println("Solo transito / Visita.");
            }
        }
    }

    // --- METODI DI SUPPORTO PER PULIRE IL CODICE ---

    private void gestisciTerreno(Giocatore g, Terreno t) {
        if (t.getProprietario() == null) {
            proponiAcquisto(g, t);
        } else if (t.getProprietario() != g) {
            pagaAffitto(g, t.getProprietario(), t.valoreRendita());
        }
    }

    private void gestisciStazione(Giocatore g, StazioneTreno s) {
        if (s.getProprietario() == null) {
            proponiAcquisto(g, s);
        } else if (s.getProprietario() != g) {
            pagaAffitto(g, s.getProprietario(), s.valoreRendita());
        }
    }
    
    private void gestisciSocieta(Giocatore g, Societa s) {
        if (s.getProprietario() == null) {
            proponiAcquisto(g, s);
        } else if (s.getProprietario() != g) {
            pagaAffitto(g, s.getProprietario(), s.affitto()); 
        }
    }

    private void proponiAcquisto(Giocatore g, Casella c) {
        // Qui assumiamo che Casella abbia getCosto(), altrimenti fai cast specifico
        // Per semplicità uso Terreno come esempio generico, ma andrebbe adattato
        int costo = 0;
        if(c instanceof Terreno) costo = ((Terreno)c).getPrezzoAcquisto(); // Assumo metodo getCostoAcquisto
        else if(c instanceof StazioneTreno) costo = 200; // Prezzo fisso stazioni
        else if(c instanceof Societa) costo = 150; // Prezzo fisso società

        System.out.println("Costo: " + costo + ". Vuoi acquistare? (s/n)");
        String risposta = Leggi.unoString(); // Classe Leggi ipotetica
        
        if (risposta.equalsIgnoreCase("s")) {
            // Nota: I metodi acquistaTerreno/Stazione devono gestire la detrazione soldi
            boolean esito = false;
            if(c instanceof Terreno) esito = g.acquistaTerreno((Terreno)c);
            else if(c instanceof StazioneTreno) esito = g.acquistaStazione((StazioneTreno)c);
            else if(c instanceof Societa) esito = g.acquistaSocieta((Societa)c);

            if(esito) System.out.println("Acquisto effettuato!");
            else System.out.println("Fondi insufficienti.");
        }
    }

    private void pagaAffitto(Giocatore pagante, Giocatore proprietario, int importo) {
        System.out.println("Proprietà di " + proprietario.getNome() + ". Affitto: " + importo);
        if (!pagante.pagaAffitto(proprietario, importo)) {
            gestisciBancarotta(pagante);
        }
    }

    private void gestisciTasse(Giocatore g, Tasse t) {
        System.out.println("Tassa da pagare: " + t.getValore());
        if (!g.pagaTassa(t.getValore())) {
            gestisciBancarotta(g);
        }
    }
    
    // Logica carte unificata per evitare duplicati
    private void gestisciImprevisti(Giocatore g, Imprevisti i) {
        gestisciCarta(g, i); 
    }
    
    private void gestisciProbabilita(Giocatore g, Probabilita p) {
        gestisciCarta(g, p);
    }
    
    // Metodo generico per carte (da adattare se le classi sono molto diverse)
    private void gestisciCarta(Giocatore g, Casella carta) {
        int numero = (int) (Math.random() * 6); 
        int soldi = 0;
        String descrizione="";
        if(carta instanceof Imprevisti){
            descrizione=((Imprevisti)carta).pescaCarta(numero);
            soldi = ((Imprevisti)carta).getSoldi(numero);
        } else{
            descrizione=((Probabilita)carta).pescaCarta(numero);
            soldi = ((Probabilita)carta).getSoldi(numero);
        } 
        if (numero==1 && carta instanceof Imprevisti) { // Vai in prigione
            System.out.println("ARRESTO! " + g.getNome() + " viene spedito in prigione.");
            g.setCasella(tabelloneArray[10]); // Sposta fisicamente al transito
            g.setInPrigione(true);
            g.setTurniFermo(3);
        }else if(numero==5 && carta instanceof Probabilita){ // Vai al VIA
            System.out.println(g.getNome() + " va al VIA. Ritira 200.");
            g.setCasella(tabelloneArray[0]);
            g.riceviSoldi(200); // Ritira 200 per passaggio VIA
        } else if(numero==3 && carta instanceof Imprevisti){
            System.out.println(g.getNome() + " avanza di 3 caselle.");
            for(int i=0;i<3;i++){
                g.setCasella(g.getCasella().getSuccessiva());
            }
            System.out.println("Nuova posizione: " + g.getCasella().getNome());
            // Gestione eventuale della nuova casella
            eseguiTurno(g); // Attenzione: potrebbe causare loop se non gestito bene
        }
        
        if (soldi < 0) {
            System.out.println("Descrizione: " + descrizione);
            System.out.println("Malus: devi pagare " + (-soldi));
            if (!g.pagaTassa(-soldi)) gestisciBancarotta(g);
        } else {
            System.out.println("Descrizione: " + descrizione);
            System.out.println("Bonus: ricevi " + soldi);
            g.riceviSoldi(soldi); // Metodo ipotetico in Giocatore
        }
    }

    public void gestisciBancarotta(Giocatore giocatore) {
        System.out.println("\n!!! " + giocatore.getNome() + " È IN BANCAROTTA ED ESCE DAL GIOCO !!!");
        // Trova il giocatore nell'array e mettilo a null
        for (int i = 0; i < listaGiocatori.length; i++) {
            if (listaGiocatori[i] == giocatore) {
                listaGiocatori[i] = null;
                break;
            }
        }
    }

    public boolean verificaVincitore() {
        int conta = 0;
        for (Giocatore g : listaGiocatori) {
            if (g != null) conta++;
        }
        return conta == 1; // Ritorna true solo se ne è rimasto uno
    }

    public void annunciaVincitore() {
        for (Giocatore g : listaGiocatori) {
            if (g != null) {
                System.out.println("\n***********************************");
                System.out.println("IL VINCITORE È: " + g.getNome().toUpperCase());
                System.out.println("***********************************");
            }
        }
    }
}