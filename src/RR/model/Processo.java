package RR.model;

import SJF.util.Constantes;

import java.util.Random;

public class Processo {

    private int id;
    private Integer duracao;
    private Integer tempoRestante;
    private int estado;
    private int prioridade;
    private int quantum;

    public Processo() {
        this.id = ++Constantes.IDS;
        do {
            this.duracao = new Random().nextInt(21);
        } while (!(this.duracao >= 4 && this.duracao <= 20));
        this.tempoRestante = getDuracao();
        this.estado = Estado.APTO.getValor();
        this.prioridade = new Random().nextInt(4);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Integer getTempoRestante() {
        return tempoRestante;
    }

    public void decrementarTempoRestante() {
        if (--this.tempoRestante == 0) {
            this.estado = Estado.FINALIZADO.getValor();
        }
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public String toString() {
        return "Processo{" +
                "id=" + id +
                ", duracao=" + duracao +
                ", tempoRestante=" + tempoRestante +
                ", estado=" + estado +
                ", prioridade=" + prioridade +
                '}';
    }
}
