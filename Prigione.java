public class Prigione extends Casella {
    int turni;

    public Prigione(String nome, Casella successiva, Casella precedente, int numeroCasella) {
        super(nome, successiva, precedente, numeroCasella);
        this.turni = 0;
    }    

    public String toString(){
        return "Prigione{" +
                "turni=" + turni +
                "} " + super.toString();
    }
}