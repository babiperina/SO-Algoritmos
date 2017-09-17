package SJF.model;

public enum Estado {
    EXECUTANDO(1), APTO(2), FINALIZADO(3);

    private final int valor;

    Estado(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }
}
