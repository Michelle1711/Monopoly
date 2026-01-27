
public class Tabellone{
    private Casella[] tabellone;

    public Tabellone(){
        this.tabellone = new Casella[40];
        inizializzaTabellone();
    }

    private void inizializzaTabellone(){
        // PRIMA PASS: Creazione di tutte le caselle senza il linking
        tabellone[0] = new Casella("VIA!", null, null, 1);
        
        //blocco Marrone e Azzurro
        tabellone[1] = new Terreno("Vicolo Corto", null, null, 2, 60, Colore.MARRONE, null);
        tabellone[2] = new Probabilita("Probabilità", null, null, 3);
        tabellone[3] = new Terreno("Vicolo Stretto", null, null, 4, 60, Colore.MARRONE, null);
        tabellone[4] = new Tasse("Tassa sul reddito", null, null, 5, 200);
        tabellone[5] = new StazioneTreno("Stazione Sud", null, null, 6);
        tabellone[6] = new Terreno("Bastioni Gran Sasso", null, null, 7, 100, Colore.AZZURRO, null);
        tabellone[7] = new Imprevisti("Imprevisti", null, null, 8);
        tabellone[8] = new Terreno("Viale Monterosa", null, null, 9, 100, Colore.AZZURRO, null);
        tabellone[9] = new Terreno("Viale Vesuvio", null, null, 10, 120, Colore.AZZURRO, null);

        // prigione e blocco Rosa e Arancio
        tabellone[10] = new Prigione("Prigione", null, null, 11);
        tabellone[11] = new Terreno("Via Accademia", null, null, 12, 140, Colore.ROSA, null);
        tabellone[12] = new Societa("Società Elettrica", null, null, 13);
        tabellone[13] = new Terreno("Corso Ateneo", null, null, 14, 140, Colore.ROSA, null);
        tabellone[14] = new Terreno("Piazza Università", null, null, 15, 160, Colore.ROSA, null);
        tabellone[15] = new StazioneTreno("Stazione Ovest", null, null, 16);
        tabellone[16] = new Terreno("Via Verdi", null, null, 17, 180, Colore.ARANCIONE, null);
        tabellone[17] = new Probabilita("Probabilità", null, null, 18);
        tabellone[18] = new Terreno("Corso Raffaello", null, null, 19, 180, Colore.ARANCIONE, null);
        tabellone[19] = new Terreno("Piazza Dante", null, null, 20, 200, Colore.ARANCIONE, null);

        // blocco Rosso e Giallo
        tabellone[20] = new Casella("PARCHEGGIO GRATUITO!", null, null, 21);
        tabellone[21] = new Terreno("Via Marco Polo", null, null, 22, 220, Colore.ROSSO, null);
        tabellone[22] = new Imprevisti("Imprevisti", null, null, 23);
        tabellone[23] = new Terreno("Corso Magellano", null, null, 24, 220, Colore.ROSSO, null);
        tabellone[24] = new Terreno("Largo Colombo", null, null, 25, 240, Colore.ROSSO, null);
        tabellone[25] = new StazioneTreno("Stazione Nord", null, null, 26);
        tabellone[26] = new Terreno("Viale Costantino", null, null, 27, 260, Colore.GIALLO, null);
        tabellone[27] = new Terreno("Viale Traiano", null, null, 28, 260, Colore.GIALLO, null);
        tabellone[28] = new Societa("Società Acqua Potabile", null, null, 29);
        tabellone[29] = new Terreno("Piazza Giulio Cesare", null, null, 30, 280, Colore.GIALLO, null);

        // Vai in Prigione e blocco Verde e Viola
        tabellone[30] = new Prigione("VAI IN PRIGIONE!", null, null, 31);
        tabellone[31] = new Terreno("Via Roma", null, null, 32, 300, Colore.VERDE, null);
        tabellone[32] = new Terreno("Corso Impero", null, null, 33, 300, Colore.VERDE, null);
        tabellone[33] = new Probabilita("Probabilità", null, null, 34);
        tabellone[34] = new Terreno("Largo Augusto", null, null, 35, 320, Colore.VERDE, null);
        tabellone[35] = new StazioneTreno("Stazione Est", null, null, 36);
        tabellone[36] = new Imprevisti("Imprevisti", null, null, 37);
        tabellone[37] = new Terreno("Viale dei Giardini", null, null, 38, 350, Colore.BLU, null);
        tabellone[38] = new Tasse("Tassa di lusso", null, null, 39, 100);
        tabellone[39] = new Terreno("Parco della Vittoria", null, null, 40, 400, Colore.BLU, null);

        // SECONDA PASS: Collegamento delle caselle in catena circolare
        for (int i = 0; i < 40; i++) {
            tabellone[i].setSuccessiva(tabellone[(i + 1) % 40]);
            tabellone[i].setPrecedente(tabellone[(i - 1 + 40) % 40]);
        }
    }

    public Casella getCasella(int indice){
        return tabellone[indice];
    }

    public Casella[] getTabellone(){
        return tabellone;
    }

    public String stampa(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabellone.length; i++) {
            sb.append(i).append(": ").append(tabellone[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public void stampaTabellone() {
        int lato = 11;
        String[][] griglia = new String[lato][lato];
        
        // Codice per resettare il colore (torna bianco/standard)
        String RESET = "\u001B[0m"; 

        // Inizializza tutto il centro come vuoto
        for (int i = 0; i < lato; i++) {
            for (int j = 0; j < lato; j++) {
                griglia[i][j] = "      "; // 6 spazi vuoti
            }
        }

        // Riempie il perimetro
        for (int i = 0; i < 40; i++) {
            String nome = tabellone[i].getNome();
            String codiceColore = ""; // Di base nessun colore

            // Se la casella è un Terreno, recuperiamo il suo colore
            if (tabellone[i] instanceof Terreno) {
                Colore col = ((Terreno) tabellone[i]).getColore();
                if (col != null) {
                    codiceColore = col.getCodice(); // Usa il metodo getter dell'Enum
                }
            } 
            // Opzionale: Colora di ROSSO la prigione per evidenziarla
            else if (tabellone[i] instanceof Prigione) {
                codiceColore = "\u001B[31m"; 
            }

            // Formattiamo il nome a 4 caratteri
            String nomeFormattato = String.format("%-4.4s", nome);

            // Costruiamo la cella: COLORE + [NOME] + RESET
            // Nota: Il reset è fondamentale, altrimenti colora anche le caselle successive!
            String cella = codiceColore + "[" + nomeFormattato + "]" + RESET;

            // Logica di posizionamento nella matrice (invariata)
            if (i <= 10) griglia[10][10 - i] = cella;        // Basso
            else if (i <= 20) griglia[20 - i][0] = cella;    // Sinistra
            else if (i <= 30) griglia[0][i - 20] = cella;    // Alto
            else griglia[i - 30][10] = cella;                // Destra
        }

        // Stampa finale
        System.out.println("\n--- TABELLONE DI GIOCO ---");
        for (int i = 0; i < lato; i++) {
            for (int j = 0; j < lato; j++) {
                System.out.print(griglia[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void stampaTabelloneGioco(Giocatore[] listaGiocatori) {
        int lato = 11; 
        String[][] griglia = new String[lato][lato];
        
        // 1. Pulizia griglia centrale
        for (int i = 0; i < lato; i++) {
            for (int j = 0; j < lato; j++) {
                griglia[i][j] = "           "; // 11 spazi vuoti (cella più larga)
            }
        }

        // 2. Riempimento perimetro
        for (int i = 0; i < 40; i++) {
            Casella casellaAttuale = tabellone[i];
            
            // A. Recupera il Colore (se presente) per l'estetica
            String coloreStart = "";
            String coloreReset = "\u001B[0m"; // Reset standard
            if (casellaAttuale instanceof Terreno) {
                coloreStart = ((Terreno) casellaAttuale).getColore().getCodice();
            }

            // B. Cerca quali giocatori sono su questa casella
            StringBuilder pedine = new StringBuilder();
            for (Giocatore g : listaGiocatori) {
                // Controllo fondamentale: g non deve essere null (giocatori eliminati)
                // e deve trovarsi sulla casella attuale (confronto per riferimento oggetto)
                if (g != null && g.getCasella() == casellaAttuale) {
                    pedine.append(g.getPedina()); // Aggiunge la pedina (es. "X", "O")
                }
            }

            // C. Formatta la stringa: [NomeBreve  Pedine]
            // %-4.4s = Prende i primi 4 caratteri del nome
            // %-3s   = Spazio per le pedine (fino a 3 caratteri)
            String nomeTroncato = String.format("%-4.4s", casellaAttuale.getNome());
            String pedineStringa = String.format("%-3s", pedine.toString());
            
            // Costruiamo la cella finale: [Nome Pedine] colorata
            String cella = coloreStart + "[" + nomeTroncato + "|" + pedineStringa + "]" + coloreReset;

            // D. Mappatura sulla matrice (identica a prima)
            if (i <= 10) griglia[10][10 - i] = cella;        // Basso
            else if (i <= 20) griglia[20 - i][0] = cella;    // Sinistra
            else if (i <= 30) griglia[0][i - 20] = cella;    // Alto
            else griglia[i - 30][10] = cella;                // Destra
        }

        // 3. Stampa a video
        System.out.println("\n--- TABELLONE (Legenda: [Nome|Pedine]) ---");
        for (int i = 0; i < lato; i++) {
            for (int j = 0; j < lato; j++) {
                System.out.print(griglia[i][j] + " ");
            }
            System.out.println();
        }
    }
}