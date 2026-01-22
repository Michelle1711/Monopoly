public class Monopoly{
    public static void main(String[] args) {
        System.out.println("Benvenuti a Monopoly!");
        System.out.println("Ecco il tabellone di gioco:");
        Tabellone tabellone = new Tabellone();
        System.out.println(tabellone.stampaTabellone());
        tabellone.stampaGrafica();
    }
}