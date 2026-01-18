
public class Tabellone{
    private Casella[] tabellone;

    public Tabellone(){
        this.tabellone = new Casella[40];
        inizializzaTabellone();
    }

    private void inizializzaTabellone(){
        tabellone[0] = new Casella("VIA!",tabellone[1],tabellone[39],1);
        //blocco Marrone e Azzurro
        tabellone[1] = new Terreno("Vicolo Corto",tabellone[2],tabellone[0], 2, 60,Colore.MARRONE);
        tabellone[2] = new Probabilita();
        tabellone[3] = new Terreno("Vicolo Stretto",tabellone[4],tabellone[3],4,60,Colore.MARRONE);
        tabellone[4] = new Tasse(200); 
        tabellone[5] = new StazioneTreno("Stazione Sud");
        tabellone[6] = new Terreno("Bastioni Gran Sasso",tabellone[7],tabellone[5],7,100,Colore.AZZURRO);
        tabellone[7] = new Imprevisti();
        tabellone[8] = new Terreno("Viale Monterosa",tabellone[9],tabellone[7],9,100,Colore.AZZURRO);
        tabellone[9] = new Terreno("Viale Vesuvio",tabellone[10],tabellone[8],10,120,Colore.AZZURRO);

        // prigione e blocco Rosa e Arancio
        tabellone[10] = new Prigione("PRIGIONE!",tabellone[11],tabellone[9],11);
        tabellone[11] = new Terreno("Via Accademia",tabellone[12],tabellone[10],12,140,Colore.ROSA);
        tabellone[12] = new Societa("Società Elettrica");
        tabellone[13] = new Terreno("Corso Ateneo",tabellone[14],tabellone[12],14,140,Colore.ROSA);
        tabellone[14] = new Terreno("Piazza Università",tabellone[15],tabellone[13],15,160,Colore.ROSA);
        tabellone[15] = new StazioneTreno("Stazione Ovest");
        tabellone[16] = new Terreno("Via Verdi",tabellone[17],tabellone[15],17,180,Colore.ARANCIONE);
        tabellone[17] = new Probabilita();
        tabellone[18] = new Terreno("Corso Raffaello",tabellone[19],tabellone[17],19,180,Colore.ARANCIONE);
        tabellone[19] = new Terreno("Piazza Dante",tabellone[20],tabellone[18],20,200,Colore.ARANCIONE);

        // blocco Rosso e Giallo
        tabellone[20] = new Casella("PARCHEGGIO GRATUITO!",tabellone[21],tabellone[19],21);
        tabellone[21] = new Terreno("Via Marco Polo",tabellone[22],tabellone[20],22,220,Colore.ROSSO);
        tabellone[22] = new Imprevisti();
        tabellone[23] = new Terreno("Corso Magellano",tabellone[24],tabellone[22],24,220,Colore.ROSSO);
        tabellone[24] = new Terreno("Largo Colombo",tabellone[25],tabellone[23],25,240,Colore.ROSSO);
        tabellone[25] = new StazioneTreno("Stazione Nord");
        tabellone[26] = new Terreno("Viale Costantino",tabellone[27],tabellone[25],27,260,Colore.GIALLO);
        tabellone[27] = new Terreno("Viale Traiano",tabellone[28],tabellone[26],28,260,Colore.GIALLO);
        tabellone[28] = new Societa("Società Acqua Potabile");
        tabellone[29] = new Terreno("Piazza Giulio Cesare",tabellone[30],tabellone[28],30,280,Colore.GIALLO);

        // Vai in Prigione e blocco Verde e Viola
        tabellone[30] = new Prigione("VAI IN PRIGIONE!",tabellone[31],tabellone[29],31);
        tabellone[31] = new Terreno("Via Roma",tabellone[32],tabellone[30],32,300,Colore.VERDE);
        tabellone[32] = new Terreno("Corso Impero",tabellone[33],tabellone[31],33,300,Colore.VERDE);
        tabellone[33] = new Probabilita();
        tabellone[34] = new Terreno("Largo Augusto",tabellone[35],tabellone[32],35,320,Colore.VERDE);
        tabellone[35] = new StazioneTreno("Stazione Est");
        tabellone[36] = new Imprevisti();
        tabellone[37] = new Terreno("Viale dei Giardini",tabellone[38],tabellone[35],38,350,Colore.BLU);
        tabellone[38] = new Tasse(100); // Tassa di lusso
        tabellone[39] = new Terreno("Parco della Vittoria",tabellone[0],tabellone[38],0,400,Colore.BLU);

    }

    public String stampaTabellone(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabellone.length; i++) {
            sb.append(i).append(": ").append(tabellone[i].toString()).append("\n");
        }
        return sb.toString();
    }
}