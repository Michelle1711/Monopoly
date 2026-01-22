public class Probabilita {

    private String[] mazzoProbabilita;
    private int[] soldiProbabilita;

    public Probabilita() {
        mazzoProbabilita = new String[] {
                "Ricevi 100",
                "Paga 20",
                "Hai vinto un concorso: ricevi 200",
                "Errore bancario a tuo favore: ricevi 150",
                "Paga le tasse scolastiche: 50",
                "Avanza fino al Via"
        };

        soldiProbabilita = new int[] {
                100,
                -20,
                200,
                150,
                -50,
                0
        };
    }

    public String pescaCarta(int indice) {
        return mazzoProbabilita[indice];
    }

    public int getSoldi(int indice) {
        return soldiProbabilita[indice];
    }

    public int numeroCarte() {
        return mazzoProbabilita.length;
    }

    @Override
    public String toString() {
        return "Mazzo Probabilità";
    }
}
