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
        do { 
            eseguiCicloTurno();  
            prossimoTurno();  
        } while (verificaVincitore() == false);
        vincitore();
    }

    public void prossimoTurno(){
        indiceTurno = (indiceTurno + 1) % listaGiocatori.length;
    }

    public void eseguiTurno(Giocatore giocatore){
        giocatore.muovi(new Dadi());
        Casella casellaCorrente = giocatore.getCasella();
        if(casellaCorrente instanceof Terreno){
            System.out.println(giocatore.getNome() + " è atterrato su " + casellaCorrente.getNome());
            if(((Terreno) casellaCorrente).getProprietario()==null){
                System.out.println("Questo terreno è disponibile per l'acquisto.");
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
            }else{
                Giocatore proprietario = ((Terreno) casellaCorrente).getProprietario();
                if(proprietario != giocatore){
                    int affitto = ((Terreno) casellaCorrente).valoreRendita();
                    System.out.println("Questo terreno è di proprietà di " + proprietario.getNome() + ". Deve pagare un affitto di " + affitto);
                    if(!giocatore.pagaAffitto(proprietario, affitto)){
                        System.out.println(giocatore.getNome() + " non ha abbastanza denaro per pagare l'affitto!");
                        gestisciBancarotta(giocatore);
                    }
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
            Probabilita probabilita = (Probabilita) casellaCorrente;
            int numero = (int) (Math.random() * 6);
            System.out.println(probabilita.pescaCarta(numero));
            int soldi=probabilita.getSoldi(numero);
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
        }else if(casellaCorrente instanceof Prigione){
            System.out.println(giocatore.getNome() + " è in prigione.");
            casellaCorrente = tabellone[10];
            giocatore.isPrigione();
        }else if(casellaCorrente instanceof StazioneTreno){
            System.out.println(giocatore.getNome() + " è atterrato su una Stazione Treno.");
            if(((StazioneTreno) casellaCorrente).getProprietario()==null){
                System.out.println("Questa stazione è disponibile per l'acquisto.");
                System.out.println("Vuoi acquistare questa stazione? (s/n)");
                String risposta = Leggi.unoString();
                if(risposta.equalsIgnoreCase("s")){
                    StazioneTreno stazione = (StazioneTreno) casellaCorrente;
                    if(giocatore.acquistaStazione(stazione)){
                        System.out.println(giocatore.getNome() + " ha acquistato " + stazione.getNome());
                    }else{
                        System.out.println(giocatore.getNome() + " non ha abbastanza denaro per acquistare " + stazione.getNome());
                    }
                }
            }else{
                Giocatore proprietario = ((StazioneTreno) casellaCorrente).getProprietario();
                if(proprietario != giocatore){  
                    int affitto = ((StazioneTreno) casellaCorrente).valoreRendita();
                    System.out.println("Questa stazione è di proprietà di " + proprietario.getNome() + ". Deve pagare un affitto di " + affitto);
                    if(!giocatore.pagaAffitto(proprietario, affitto)){
                        System.out.println(giocatore.getNome() + " non ha abbastanza denaro per pagare l'affitto!");
                        gestisciBancarotta(giocatore);
                    }
                }
            }
        }else if(casellaCorrente instanceof Societa){
            System.out.println(giocatore.getNome() + " è atterrato su una Società.");
            if(((Societa) casellaCorrente).getProprietario()==null){
                System.out.println("Questa società è disponibile per l'acquisto.");
                System.out.println("Vuoi acquistare questa società? (s/n)");
                String risposta = Leggi.unoString();
                if(risposta.equalsIgnoreCase("s")){
                    Societa societa = (Societa) casellaCorrente;
                    if(giocatore.acquistaSocieta(societa)){
                        System.out.println(giocatore.getNome() + " ha acquistato " + societa.getNome());
                    }else{
                        System.out.println(giocatore.getNome() + " non ha abbastanza denaro per acquistare " + societa.getNome());
                    }
                }
            }else{
                Giocatore proprietario = ((Societa) casellaCorrente).getProprietario();
                if(proprietario != giocatore){  
                    int affitto = ((Societa) casellaCorrente).affitto();
                    System.out.println("Questa società è di proprietà di " + proprietario.getNome() + ". Deve pagare un affitto di " + affitto);
                    if(!giocatore.pagaAffitto(proprietario, affitto)){
                        System.out.println(giocatore.getNome() + " non ha abbastanza denaro per pagare l'affitto!");
                        gestisciBancarotta(giocatore);
                    }
                }
            }
        }else if(casellaCorrente instanceof Casella){
            System.out.println(giocatore.getNome() + " è atterrato su " + casellaCorrente.getNome());
        }

    }

    public void gestisciBancarotta(Giocatore giocatore){
        System.out.println(giocatore.getNome() + " è in bancarotta e viene eliminato dalla partita.");
        for(int i=0;i<listaGiocatori.length;i++){
            if(listaGiocatori[i]==giocatore){
                listaGiocatori[i]=null;
                break;
            }
        }
    }

    public boolean verificaVincitore(){
        int giocatoriRimasti=0;
        Giocatore vincitore=null;
        for(int i=0;i<listaGiocatori.length;i++){
            if(listaGiocatori[i]!=null){
                giocatoriRimasti++;
                vincitore=listaGiocatori[i];
            }
        }
        if(giocatoriRimasti==1){
            return true;
        }
    }

    public void vincitore(){
        int giocatoriRimasti=0;
        Giocatore vincitore=null;
        for(int i=0;i<listaGiocatori.length;i++){
            if(listaGiocatori[i]!=null){
                giocatoriRimasti++;
                vincitore=listaGiocatori[i];
            }
        }
        if(giocatoriRimasti==1){
            System.out.println("Il vincitore è " + vincitore.getNome() + "!");
        }
    }

    public void eseguiCicloTurno(){
        while(verificaVincitore()==false && listaGiocatori[indiceTurno]!=null){
            Giocatore giocatoreCorrente = listaGiocatori[indiceTurno];
            if(giocatoreCorrente != null){
                eseguiTurno(giocatoreCorrente);
                verificaVincitore();
            }
            prossimoTurno();
        }
    }
}