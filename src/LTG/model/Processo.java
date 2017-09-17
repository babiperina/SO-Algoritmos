package LTG.model;

import LTG.util.Constantes;
import RR.model.Estado;

import java.sql.Timestamp;
import java.util.Random;

public class Processo {

    private int id;
    private Integer duracao;
    private Integer tempoRestante;
    private int estado;
    private Timestamp deadline;

    public Processo() {
        this.id = ++Constantes.IDS;
        do {
            this.duracao = new Random().nextInt(21);
        } while (!(this.duracao >= 4 && this.duracao <= 20));
        this.tempoRestante = getDuracao();
        this.estado = Estado.APTO.getValor();
        int deadline;
        do {
            deadline = new Random().nextInt(21);
        } while (!(deadline >= 4 && deadline <= 20));
        deadline *= 1000;
        this.deadline = new Timestamp(System.currentTimeMillis() + deadline);
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

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Processo{" +
                "id=" + id +
                ", duracao=" + duracao +
                ", tempoRestante=" + tempoRestante +
                ", estado=" + estado +
                ", deadline=" + deadline +
                '}';
    }
}
