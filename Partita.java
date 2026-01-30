public class Partita {
    private Giocatore[] listaGiocatori;
    private Banca banca;
    private int indiceTurno;
    private Tabellone tabellone; // Oggetto Tabellone per la stampa con pedine
    private Casella[] tabelloneArray; // Array di caselle per accedere direttamente
    private Dadi dadi; // I dadi è meglio crearli una volta sola
    private int contatoreTurni; // Contatore per evitare loop infinito
    private boolean modalitaCheat;

    public Partita(Giocatore[] listaGiocatori, Banca banca, Tabellone tabellone) {
        this.listaGiocatori = listaGiocatori;
        this.banca = banca;
        this.tabellone = tabellone;
        this.tabelloneArray = tabellone.getTabellone();
        this.indiceTurno = 0;
        this.dadi = new Dadi(); 
        this.contatoreTurni = 0;
        this.modalitaCheat = false;
    }

    public void avviaPartita() {
        chiediModalitaCheat();

        System.out.println("--- MONOPOLY: La partita è iniziata! ---");
        if (modalitaCheat) System.out.println("[!] MODALITÀ CHEAT ATTIVA [!]");

        tabellone.stampaTabelloneGioco(listaGiocatori); // Stampa il tabellone con le pedine
        
        // Ciclo principale del gioco
        while (!verificaVincitore()) {
            
            Giocatore giocatoreCorrente = listaGiocatori[indiceTurno];
            
            // Se il giocatore non è stato eliminato (non è null), gioca
            if (giocatoreCorrente != null) {
                contatoreTurni++;
                System.out.println("\n--------------------------------");
                System.out.println("Turno " + contatoreTurni + ": " + giocatoreCorrente.getNome() + " [Budget: " + giocatoreCorrente.getBudget() + "]");
                //System.out.println(giocatoreCorrente.getProprietaPossedute());
                giocatoreCorrente.stampaProprietaPossedute();
                System.out.println();
                eseguiTurno(giocatoreCorrente);
            }
            
            // Passa al prossimo, saltando eventuali giocatori eliminati
            passaAlProssimoGiocatoreValido();
        }
        
        annunciaVincitore();
    }

    private void chiediModalitaCheat() {
        System.out.println("Vuoi attivare la modalità CHEAT (scegli tu i dadi)? (s/n)");
        // Uso Leggi.unoString() ipotizzando la tua classe, altrimenti usa Scanner
        String risposta = Leggi.unoString(); 
        
        if (risposta.equalsIgnoreCase("s")) {
            this.modalitaCheat = true;
            System.out.println(">> Trucchi attivati! Ti verrà chiesto il valore dei dadi ogni turno.");
        } else {
            this.modalitaCheat = false;
        }
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
        if (modalitaCheat) {
            int valoreForzato;
            do{
                System.out.println("[CHEAT] Inserisci il valore dei dadi (2-12) per " + giocatore.getNome() + ": ");
                valoreForzato = Leggi.unInt(); // Legge l'intero da tastiera
            } while (valoreForzato < 2 || valoreForzato > 12);
            // Imposta il valore "truccato" nell'oggetto dadi
            dadi.setValoreTruccato(valoreForzato);
        }
        dadi.lancia();

        giocatore.muovi(dadi); 
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
        
        // CASO 1: Il terreno non ha proprietario -> PROPOSTA ACQUISTO
        if (t.getProprietario() == null) {
            proponiAcquisto(g, t);
        } 
        
        // CASO 2: Il terreno è di un altro giocatore -> PAGA AFFITTO
        else if (t.getProprietario() != g) {
            int affitto = t.valoreRendita();

            // REGOLA MONOPOLY: Se il proprietario ha il set completo ma 0 case, l'affitto base raddoppia
            if (t.getnCase() == 0 && t.getProprietario().haSetCompleto(t.getColore())) {
                System.out.println("Il proprietario possiede tutto il set " + t.getColore() + "! L'affitto è raddoppiato.");
                affitto *= 2;
            }

            pagaAffitto(g, t.getProprietario(), affitto);
        } 
        
        // CASO 3: Il terreno è del giocatore corrente -> COSTRUZIONE
        else {
            System.out.println("Sei sulla tua proprietà: " + t.getNome());
            
            // Verifica: Ha il set completo? E non ha già un albergo (5 case)?
            if (g.haSetCompleto(t.getColore())) {
                
                if (t.getnCase() < 5) {
                    int costoCasa = t.getCostoCasa();
                    System.out.println("Possiedi tutti i terreni " + t.getColore() + "!");
                    System.out.println("Attualmente hai " + t.getnCase() + " case/alberghi.");
                    System.out.println("Vuoi costruire per " + costoCasa + "€? (s/n)");
                    
                    String risposta = Leggi.unoString(); // Usa il tuo metodo di input
                    
                    if (risposta.equalsIgnoreCase("s")) {
                        // Verifica se ha i soldi
                        if (g.getBudget() >= costoCasa) {
                            g.pagaTassa(costoCasa); // Scala i soldi dal giocatore
                            t.costruisciCasa(); // Aumenta il numero di case nel terreno
                            System.out.println("Costruzione completata! Ora l'affitto qui sarà: " + t.valoreRendita());
                        } else {
                            System.out.println("Non hai abbastanza fondi per costruire.");
                        }
                    }
                } else {
                    System.out.println("Hai già un ALBERGO (massimo livello di costruzione).");
                }
            } else {
                System.out.println("Non puoi costruire: ti mancano altri terreni del colore " + t.getColore() + ".");
            }
        }
    }

    private void gestisciStazione(Giocatore g, StazioneTreno s) {
        // CASO 1: Nessun proprietario -> Proponi acquisto
        if (s.getProprietario() == null) {
            proponiAcquisto(g, s);
        } 
        // CASO 2: Di proprietà altrui -> Paga affitto calcolato
        else if (s.getProprietario() != g) {
            Giocatore proprietario = s.getProprietario();
            
            // 1. Recuperiamo quante stazioni ha il proprietario
            int numeroStazioniPossedute = proprietario.getNumeroStazioni();
            
            // 2. Calcolo Affitto
            // Regola standard Monopoly: 1 staz=25, 2 staz=50, 3 staz=100, 4 staz=200.
            // Formula matematica: 25 * 2^(n-1)
            int affittoBase = 25; 
            int affittoDaPagare = affittoBase*proprietario.getNumeroStazioni();

            /* NOTA: Se invece preferisci una moltiplicazione semplice (es. 2 stazioni = 50, 3 = 75),
               usa questa riga al posto di quella sopra:
               
               int affittoDaPagare = affittoBase * numeroStazioniPossedute;
            */

            System.out.println("Il proprietario " + proprietario.getNome() + " possiede " + numeroStazioniPossedute + " stazioni.");
            
            // 3. Pagamento
            pagaAffitto(g, proprietario, affittoDaPagare);
        }
    }
    
    private void gestisciSocieta(Giocatore g, Societa s) {
        // CASO 1: Nessun proprietario -> Proponi acquisto
        if (s.getProprietario() == null) {
            proponiAcquisto(g, s);
        } 
        // CASO 2: Di proprietà altrui -> Paga affitto
        else if (s.getProprietario() != g) {
            Giocatore proprietario = s.getProprietario();
            
            // Recuperiamo il prezzo base (l'affitto standard della società)
            int affittoDaPagare = s.affitto(); 
            
            // Contiamo quante società possiede il proprietario
            // (Nota: devi aver aggiunto getNumeroSocieta() in Giocatore, vedi sotto)
            int numeroSocieta = proprietario.getNumeroSocieta();

            // SE il proprietario ha tutte e due le società (quindi 2), moltiplica per 4
            if (numeroSocieta >= 2) {
                System.out.println("Il proprietario possiede entrambe le società! Il prezzo è moltiplicato per 4.");
                affittoDaPagare = affittoDaPagare * 4;
            }

            pagaAffitto(g, proprietario, affittoDaPagare); 
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
            tabellone.stampaTabelloneGioco(listaGiocatori);
        }else if(numero==5 && carta instanceof Probabilita){ // Vai al VIA
            System.out.println(g.getNome() + " va al VIA. Ritira 200.");
            g.setCasella(tabelloneArray[0]);
            g.riceviSoldi(200); // Ritira 200 per passaggio VIA
            tabellone.stampaTabelloneGioco(listaGiocatori);
        } else if(numero==3 && carta instanceof Imprevisti){
            System.out.println(g.getNome() + " avanza di 3 caselle.");
            for(int i=0;i<3;i++){
                g.setCasella(g.getCasella().getSuccessiva());
            }
            Casella casellaCorrente = g.getCasella();
            System.out.println("Nuova posizione: " + g.getCasella().getNome());
            tabellone.stampaTabelloneGioco(listaGiocatori); 
            if (casellaCorrente instanceof Terreno) {
                gestisciTerreno(g, (Terreno) casellaCorrente);
            } 
            else if (casellaCorrente instanceof StazioneTreno) {
                gestisciStazione(g, (StazioneTreno) casellaCorrente);
            }
            else if (casellaCorrente instanceof Societa) {
                gestisciSocieta(g, (Societa) casellaCorrente);
            }
            else if (casellaCorrente instanceof Tasse) {
                gestisciTasse(g, (Tasse) casellaCorrente);
            }
            else if (casellaCorrente instanceof Imprevisti) {
                gestisciImprevisti(g, (Imprevisti) casellaCorrente);
            }
            else if (casellaCorrente instanceof Probabilita) {
                gestisciProbabilita(g, (Probabilita) casellaCorrente);
            }
            else if (casellaCorrente instanceof Prigione) {
                // Se è la casella "Vai in prigione" (indice 30)
                if (casellaCorrente.getNome().equalsIgnoreCase("VAI IN PRIGIONE!")) {
                    System.out.println("ARRESTO! " + g.getNome() + " viene spedito in prigione.");
                    g.setCasella(tabelloneArray[10]); // Sposta fisicamente al transito
                    g.setInPrigione(true);
                    g.setTurniFermo(3);
                } else {
                    System.out.println("Solo transito / Visita.");
                }
            }
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