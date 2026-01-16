public class Prigione extends Casella {
    int turni;

    public Prigione(int turni){
        this.turni = turni;
    }
    public String toString(){
        return "Prigione: " + turni + " turni rimanenti.";
    }
}