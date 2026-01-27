public class Monopoly{
    public static void main(String[] args) {
        System.out.println("Benvenuti a Monopoly!");
        System.out.println("Ecco il tabellone di gioco:");
        Tabellone tabellone = new Tabellone();
        tabellone.stampaTabellone();

        int nGiocatori=0;
        do{
            System.out.println("Insersci il numero di giocatori (2-6):");
            nGiocatori=Leggi.unInt();
        }while(nGiocatori<2 || nGiocatori>6);

        Giocatore[] giocatori = new Giocatore[nGiocatori];
        for(int i=0;i<nGiocatori;i++){
            System.out.println("Inserisci il nome del giocatore " + (i+1) + ":");
            String nome=Leggi.unoString();
            int sceltaPedina=0;
            do{
                System.out.println("Scegli la pedina per " + nome + " (1-Carro, 2-Cane, 3-Cappello, 4-Barca, 5-Cane, 6-Zaino):");
                sceltaPedina=Leggi.unInt();
            }while(sceltaPedina<1 || sceltaPedina>6);

            giocatori[i]=new Giocatore(nome, String.valueOf(sceltaPedina), tabellone.getCasella(0));
        }
        Partita partita=new Partita(giocatori,new Banca(tabellone.getTabellone()),tabellone);
        partita.avviaPartita();
    }
}