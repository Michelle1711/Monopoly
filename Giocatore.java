import java.util.ArrayList;

public class Giocatore {
    private String nome;
    private String pedina;
    private int denaro;
    // Usiamo ArrayList perché la lista si allunga durante il gioco
    private ArrayList<Casella> proprietaPossedute; 
    private Casella casellaCorrente;
    
    private int nDoppio = 0;
    private int turniFermo = 0;
    private boolean inPrigione = false;

    // Costruttore Semplificato: Inizializza lo stato di partenza standard
    public Giocatore(String nome, String pedina, Casella partenza) {
        this.nome = nome;
        this.pedina = pedina;
        this.denaro = 1500; // Budget iniziale standard del Monopoly
        this.proprietaPossedute = new ArrayList<>();
        this.casellaCorrente = partenza;
    }

    // --- MOVIMENTO E POSIZIONE ---

    public void muovi(Dadi dado) {
        int passi = dado.getValoreDado(); // Assumo Dadi abbia getValoreTotale()
        
        System.out.println(nome + " lancia i dadi e ottiene: "  + passi);

        // Gestione regola dei 3 doppi consecutivi
        if (dado.isDoppio()) {
            nDoppio++;
        } else {
            nDoppio = 0;
        }

        if (nDoppio == 3 && dado.getValoreImpostato()==false) {
            System.out.println("Tre doppi consecutivi! Vai in prigione.");
            nDoppio = 0;
            inPrigione = true;
            turniFermo = 3; // Deve stare 3 turni in prigione
            return; 
        }

        // Movimento passo passo per intercettare il passaggio dal VIA
        for (int i = 0; i < passi; i++) {
            casellaCorrente = casellaCorrente.getSuccessiva();
            if (casellaCorrente.getNome().equalsIgnoreCase("VIA!")) {
                System.out.println("Passaggio dal VIA! Ritiri 200.");
                denaro += 200;
            }
        }
    }

    public void setCasella(Casella c) {
        this.casellaCorrente = c;
    }

    public Casella getCasella() {
        return casellaCorrente;
    }

    // --- GESTIONE PRIGIONE ---

    public boolean isInPrigione() {
        return inPrigione;
    }

    public void setInPrigione(boolean inPrigione) {
        this.inPrigione = inPrigione;
    }

    public int getTurniFermo() {
        return turniFermo;
    }

    public void setTurniFermo(int turniFermo) {
        this.turniFermo = turniFermo;
    }

    public void decrementaTurniFermo() {
        if (turniFermo > 0) turniFermo--;
    }

    // --- GESTIONE ECONOMICA ---

    public int getBudget() {
        return denaro;
    }

    public void riceviSoldi(int importo) {
        this.denaro += importo;
    }

    public boolean pagaTassa(int importo) {
        if (denaro >= importo) {
            denaro -= importo;
            return true;
        }
        return false; // Segnale di bancarotta
    }

    public boolean pagaAffitto(Giocatore proprietario, int importo) {
        if (denaro >= importo) {
            denaro -= importo;
            proprietario.riceviSoldi(importo);
            return true;
        }
        // Se non ha abbastanza soldi, paga quello che ha e va in bancarotta
        proprietario.riceviSoldi(denaro);
        denaro = 0;
        return false;
    }

    // --- ACQUISTO PROPRIETÀ ---

    // Metodo generico per acquistare qualsiasi proprietà (Terreno, Stazione, Società)
    // Assumiamo che abbiano metodi comuni o gestiamo con instanceof
    
    public boolean acquistaTerreno(Terreno t) {
        if (denaro >= t.getPrezzoAcquisto()) { // Assumo getPrezzoAcquisto() in Terreno
            denaro -= t.getPrezzoAcquisto();
            proprietaPossedute.add(t);
            t.setProprietario(this); // FONDAMENTALE: Diciamo al terreno che è nostro
            return true;
        }
        return false;
    }

    public boolean acquistaStazione(StazioneTreno s) {
        if (denaro >= 200) { // Prezzo fisso o s.getPrezzo()
            denaro -= 200;
            proprietaPossedute.add(s);
            s.setProprietario(this);
            return true;
        }
        return false;
    }

    public boolean acquistaSocieta(Societa s) {
        if (denaro >= 150) { // Prezzo fisso o s.getPrezzo()
            denaro -= 150;
            proprietaPossedute.add(s);
            s.setProprietario(this);
            return true;
        }
        return false;
    }
    
    // Conta quante stazioni possiede (serve per calcolare la rendita della stazione)
    public int getNumeroStazioni() {
        int count = 0;
        for (Casella c : proprietaPossedute) {
            if (c instanceof StazioneTreno) count++;
        }
        return count;
    }

    // Conta quante società possiede
    public int getNumeroSocieta() {
        int count = 0;
        for (Casella c : proprietaPossedute) {
            if (c instanceof Societa) count++;
        }
        return count;
    }

    // --- LOGICA SET COMPLETI ---

    public boolean haSetCompleto(Colore colore) {
        int contatore = 0;
        for (Casella c : proprietaPossedute) {
            if (c instanceof Terreno) {
                if (((Terreno) c).getColore() == colore) {
                    contatore++;
                }
            }
        }
        
        // Logica standard Monopoly
        if (colore == Colore.MARRONE || colore == Colore.BLU) {
            return contatore == 2;
        } else {
            return contatore == 3;
        }
    }

    // --- ALTRO ---

    public String getNome() {
        return nome;
    }

    public String getPedina() {
        return pedina;
    }
    public String getProprietaPossedute() {
        StringBuilder sb = new StringBuilder();
        for (Casella c : proprietaPossedute) {
            sb.append(c.getNome()).append(", ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // Rimuove l'ultima virgola e spazio
        }
        return sb.toString();
    }

    public void stampaProprietaPossedute(){
        String RESET = "\u001B[0m"; 
        String codiceColore = "";
        String cella="";
        System.out.print("Proprietà possedute: ");
        for (Casella c : proprietaPossedute) {
            if(c instanceof Terreno){
                Colore col = ((Terreno) c).getColore();
                if (col != null) {
                    codiceColore = col.getCodice(); // Usa il metodo getter dell'Enum
                }
                cella = codiceColore + "[" + c.getNome() + "]" + RESET+", ";
            }else{
                cella = "[" + c.getNome() + "]"+ RESET+", ";
            }
            System.out.print(cella);
        }
        
    }

    @Override
    public String toString() {
        return "Giocatore [" + nome + " (" + pedina + ") | Budget: " + denaro + "€ | Pos: " + casellaCorrente.getNome() + "]";
    }
}