package RR.algoritmo;

import RR.model.Processo;

import java.util.ArrayList;

public class Rr implements Runnable {

    private int quantum;
    private int controlePrioridade = 0;

    Processo[] cores;
    ArrayList<Processo> P0 = new ArrayList<>();
    ArrayList<Processo> P1 = new ArrayList<>();
    ArrayList<Processo> P2 = new ArrayList<>();
    ArrayList<Processo> P3 = new ArrayList<>();

    private int quantumP0;
    private int quantumP1;
    private int quantumP2;
    private int quantumP3;

    public Rr(int qtdeCores, int qtdeProcessosIniciais, int quantum) {
        this.quantum = quantum;
        cores = new Processo[qtdeCores];

        for (int i = 0; i < qtdeProcessosIniciais; i++) {
            Processo novoProcesso = new Processo();
            alocarProcessoByPrioridade(novoProcesso);
        }

        for (int i = 0; i < cores.length && (!P0.isEmpty() || !P1.isEmpty() || !P2.isEmpty() || !P3.isEmpty()); i++) {
            cores[i] = pegarProcessoNaFilaCorreta(this.controlePrioridade);
        }
    }

    private void alocarProcessoByPrioridade(Processo processo) {
        switch (processo.getPrioridade()) {
            case 0:
                processo.setQuantum(this.quantum * 4);
                P0.add(processo);
                P0.sort((p1, p2) -> p1.getDuracao().compareTo(p2.getDuracao()));
                break;
            case 1:
                processo.setQuantum(this.quantum * 3);
                P1.add(processo);
                P1.sort((p1, p2) -> p1.getDuracao().compareTo(p2.getDuracao()));
                break;
            case 2:
                processo.setQuantum(this.quantum * 2);
                P2.add(processo);
                P2.sort((p1, p2) -> p1.getDuracao().compareTo(p2.getDuracao()));
                break;
            case 3:
                processo.setQuantum(this.quantum);
                P3.add(processo);
                P3.sort((p1, p2) -> p1.getDuracao().compareTo(p2.getDuracao()));
                break;
        }
    }

    private Processo pegarProcessoNaFilaCorreta(int controlePrioridade) {
        while (true) {
            switch (controlePrioridade) {
                case 0:
                    incrementarControlePrioridade();
                    if (!P0.isEmpty())
                        return P0.remove(0);
                    break;
                case 1:
                    incrementarControlePrioridade();
                    if (!P1.isEmpty())
                        return P1.remove(0);
                    break;
                case 2:
                    incrementarControlePrioridade();
                    if (!P2.isEmpty())
                        return P2.remove(0);
                    break;
                case 3:
                    incrementarControlePrioridade();
                    if (!P3.isEmpty())
                        return P3.remove(0);
                    break;
            }
        }
    }

    private void incrementarControlePrioridade() {
        if (++this.controlePrioridade > 3)
            controlePrioridade = 0;
    }

    @Override
    public void run() {

    }
}
