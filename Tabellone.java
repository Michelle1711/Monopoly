
public class Tabellone{
    private Casella[] tabellone;

    public Tabellone(){
        this.tabellone = new Casella[40];
        inizializzaTabellone();
    }

    private void inizializzaTabellone(){
        tabellone[0] = new Casella("VIA!",tabellone[1],tabellone[39],1);
        //blocco Marrone e Azzurro
        tabellone[1] = new Terreno("Vicolo Corto",tabellone[2],tabellone[0], 2, 60,Colore.MARRONE,null);
        tabellone[2] = new Probabilita("Probabilità",tabellone[3],tabellone[1],3);
        tabellone[3] = new Terreno("Vicolo Stretto",tabellone[4],tabellone[2],4,60,Colore.MARRONE,null);
        tabellone[4] = new Tasse("Tassa sul reddito",tabellone[5],tabellone[3],5,200);
        tabellone[5] = new StazioneTreno("Stazione Sud",tabellone[6],tabellone[4],6);
        tabellone[6] = new Terreno("Bastioni Gran Sasso",tabellone[7],tabellone[5],7,100,Colore.AZZURRO,null);
        tabellone[7] = new Imprevisti("Imprevisti",tabellone[8],tabellone[6],8);
        tabellone[8] = new Terreno("Viale Monterosa",tabellone[9],tabellone[7],9,100,Colore.AZZURRO,null);
        tabellone[9] = new Terreno("Viale Vesuvio",tabellone[10],tabellone[8],10,120,Colore.AZZURRO,null);

        // prigione e blocco Rosa e Arancio
        tabellone[10] = new Prigione("Prigione",tabellone[11],tabellone[9],11);
        tabellone[11] = new Terreno("Via Accademia",tabellone[12],tabellone[10],12,140,Colore.ROSA,null);
        tabellone[12] = new Societa("Società Elettrica",tabellone[13],tabellone[11],13);
        tabellone[13] = new Terreno("Corso Ateneo",tabellone[14],tabellone[12],14,140,Colore.ROSA,null);
        tabellone[14] = new Terreno("Piazza Università",tabellone[15],tabellone[13],15,160,Colore.ROSA,null);
        tabellone[15] = new StazioneTreno("Stazione Ovest",tabellone[16],tabellone[14],16);
        tabellone[16] = new Terreno("Via Verdi",tabellone[17],tabellone[15],17,180,Colore.ARANCIONE,null);
        tabellone[17] = new Probabilita("Probabilità",tabellone[18],tabellone[16],18);
        tabellone[18] = new Terreno("Corso Raffaello",tabellone[19],tabellone[17],19,180,Colore.ARANCIONE,null);
        tabellone[19] = new Terreno("Piazza Dante",tabellone[20],tabellone[18],20,200,Colore.ARANCIONE,null);

        // blocco Rosso e Giallo
        tabellone[20] = new Casella("PARCHEGGIO GRATUITO!",tabellone[21],tabellone[19],21);
        tabellone[21] = new Terreno("Via Marco Polo",tabellone[22],tabellone[20],22,220,Colore.ROSSO,null);
        tabellone[22] = new Imprevisti("Imprevisti",tabellone[23],tabellone[21],23);
        tabellone[23] = new Terreno("Corso Magellano",tabellone[24],tabellone[22],24,220,Colore.ROSSO,null);
        tabellone[24] = new Terreno("Largo Colombo",tabellone[25],tabellone[23],25,240,Colore.ROSSO,null);
        tabellone[25] = new StazioneTreno("Stazione Nord",tabellone[26],tabellone[24],26);
        tabellone[26] = new Terreno("Viale Costantino",tabellone[27],tabellone[25],27,260,Colore.GIALLO,null);
        tabellone[27] = new Terreno("Viale Traiano",tabellone[28],tabellone[26],28,260,Colore.GIALLO,null);
        tabellone[28] = new Societa("Società Acqua Potabile",tabellone[29],tabellone[27],29);
        tabellone[29] = new Terreno("Piazza Giulio Cesare",tabellone[30],tabellone[28],30,280,Colore.GIALLO,null);

        // Vai in Prigione e blocco Verde e Viola
        tabellone[30] = new Prigione("VAI IN PRIGIONE!",tabellone[31],tabellone[29],31);
        tabellone[31] = new Terreno("Via Roma",tabellone[32],tabellone[30],32,300,Colore.VERDE,null);
        tabellone[32] = new Terreno("Corso Impero",tabellone[33],tabellone[31],33,300,Colore.VERDE,null);
        tabellone[33] = new Probabilita("Probabilità",tabellone[34],tabellone[32],34);
        tabellone[34] = new Terreno("Largo Augusto",tabellone[35],tabellone[32],35,320,Colore.VERDE,null);
        tabellone[35] = new StazioneTreno("Stazione Est",tabellone[36],tabellone[34],36);
        tabellone[36] = new Imprevisti("Imprevisti",tabellone[37],tabellone[35],37);
        tabellone[37] = new Terreno("Viale dei Giardini",tabellone[38],tabellone[35],38,350,Colore.BLU,null);
        tabellone[38] = new Tasse("Tassa di lusso", tabellone[39], tabellone[37], 39, 100); // Tassa di lusso
        tabellone[39] = new Terreno("Parco della Vittoria",tabellone[0],tabellone[38],40,400,Colore.BLU,null);

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
        int lato = 11; // Il tabellone ha 11 caselle per lato (angoli inclusi)
        String[][] griglia = new String[lato][lato];

        // Inizializza tutto il centro come vuoto
        for (int i = 0; i < lato; i++) {
            for (int j = 0; j < lato; j++) {
                griglia[i][j] = "      "; // 6 spazi per mantenere l'allineamento
            }
        }

        // Riempie il perimetro usando l'array tabellone[40]
        for (int i = 0; i < 40; i++) {
            // Prendiamo le prime 4 lettere del nome per non sformare il quadrato
            String nome = tabellone[i].getNome();
            String cella = "[" + String.format("%-4.4s", nome) + "]";

            if (i <= 10) griglia[10][10 - i] = cella;        // Basso (da destra a sinistra)
            else if (i <= 20) griglia[20 - i][0] = cella;    // Sinistra (dal basso in alto)
            else if (i <= 30) griglia[0][i - 20] = cella;    // Alto (da sinistra a destra)
            else griglia[i - 30][10] = cella;                // Destra (dall'alto in basso)
        }

        // Stampa finale a video
        System.out.println("\n--- TABELLONE DI GIOCO ---");
        for (int i = 0; i < lato; i++) {
            for (int j = 0; j < lato; j++) {
                System.out.print(griglia[i][j] + " ");
            }
            System.out.println(); // Va a capo dopo ogni riga
        }
    }
}