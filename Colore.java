public enum Colore {
    MARRONE,
    AZZURRO,
    ROSA,
    ARANCIONE,
    ROSSO,
    GIALLO,
    VERDE,
    BLU;


    // Metodo per ottenere il codice colore ANSI
    public String getCodice() {
        switch (this) {
            case MARRONE:
                return "\u001B[38;2;139;69;19m"; // Marrone
            case AZZURRO:
                return "\u001B[38;2;0;191;255m"; // Azzurro
            case ROSA:
                return "\u001B[38;2;255;105;180m"; // Rosa
            case ARANCIONE:
                return "\u001B[38;2;255;165;0m"; // Arancione
            case ROSSO:
                return "\u001B[38;2;255;0;0m"; // Rosso
            case GIALLO:
                return "\u001B[38;2;255;255;0m"; // Giallo
            case VERDE:
                return "\u001B[38;2;0;128;0m"; // Verde
            case BLU:
                return "\u001B[38;2;0;0;255m"; // Blu
            default:
                return "\u001B[0m"; // Reset
        }
    }
}
