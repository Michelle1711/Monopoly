public class Casella
{
    private String nome;
    private Casella successiva;
    private Casella precedente;
    private int numeroCasella;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Casella getSuccessiva() {
        return successiva;
    }

    public void setSuccessiva(Casella successiva) {
        this.successiva = successiva;
    }

    public Casella getPrecedente() {
        return precedente;
    }

    public void setPrecedente(Casella precedente) {
        this.precedente = precedente;
    }

    public int getNumeroCasella() {
        return numeroCasella;
    }

    public void setNumeroCasella(int numeroCasella) {
        this.numeroCasella = numeroCasella;
    }

    public Casella(String nome, Casella successiva, Casella precedente, int numeroCasella) {
        this.nome = nome;
        this.successiva = successiva;
        this.precedente = precedente;
        this.numeroCasella = numeroCasella;
    }

    public String toString() {
        return "Casella " +
                "nome='" + nome +
                "\nnumeroCasella=" + numeroCasella + "\n";
    }
}
