public class Imprevisti {

    private String[] mazzoImprevisti;
    private int[] soldiImprevisti;

    public Imprevisti() {
        mazzoImprevisti = new String[] {
                "Paga una multa di 50",
                "Vai in prigione",
                "Paga 100€ per tasse arretrate",
                "Avanza di 3 caselle",
                "Riparazioni stradali: paga 40",
                "Multa per eccesso di velocità: paga 300"
        };

        soldiImprevisti = new int[] {
                -50,
                0,
                -100,
                0,
                -40,
                -30
        };
    }

    public String pescaCarta(int indice) {
        return mazzoImprevisti[indice];
    }

    public int getSoldi(int indice) {
        return soldiImprevisti[indice];
    }

    public int numeroCarte() {
        return mazzoImprevisti.length;
    }

    @Override
    public String toString() {
        return "Mazzo Imprevisti";
    }
}
