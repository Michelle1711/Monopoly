import java.util.Random;
//commento per push
public class Dadi {

    private int valore1;
    private int valore2;
    private int valore;
    private int valoreTruccato;
    private boolean valoreImpostato=false;

    public Dadi() {
        valore1 = 0;
        valore2 = 0;
        valore = 0;
        valoreTruccato = 0;
    }

    // Lancia i dadi
    public void lancia() {
        if (valoreTruccato > 0) {
            valore = valoreTruccato;
            valore1 = valore / 2;
            valore2 = valore - valore1;
            valoreTruccato = 0;
            valoreImpostato = true;
        }else{
            Random r = new Random();
            valore1 = r.nextInt(6) + 1;
            valore2 = r.nextInt(6) + 1;
            valore = valore1 + valore2;
        }
    }

    public int getValoreDado() {
        return valore;
    }

    public boolean isDoppio() {
        if(valoreImpostato) {
            valoreImpostato = false;
            return false; 
        }
        return valore1 == valore2;
    }
    
    public void setValoreTruccato(int valore) {
        this.valoreTruccato = valore;
    }

    @Override
    public String toString() {
        return "Dadi: " + valore1 + " - " + valore2;
    }

}
