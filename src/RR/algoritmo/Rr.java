package RR.algoritmo;

import RR.model.Estado;
import RR.model.Processo;
import RR.util.Constantes;

import java.util.ArrayList;

public class Rr implements Runnable {

    private int quantum;
    private int controlePrioridade = 0;

    Processo[] cores;
    ArrayList<Processo> P0 = new ArrayList<>();
    ArrayList<Processo> P1 = new ArrayList<>();
    ArrayList<Processo> P2 = new ArrayList<>();
    ArrayList<Processo> P3 = new ArrayList<>();
    ArrayList<Processo> finalizados = new ArrayList<>();

    public Rr(int qtdeCores, int qtdeProcessosIniciais, int quantum) {
        Constantes.RR_IS_RUNNING = true;
        this.quantum = quantum;
        cores = new Processo[qtdeCores];

        for (int i = 0; i < qtdeProcessosIniciais; i++) {
            Processo novoProcesso = new Processo();
            alocarProcessoByPrioridade(novoProcesso);
        }

        for (int i = 0; i < cores.length && (!P0.isEmpty() || !P1.isEmpty() || !P2.isEmpty() || !P3.isEmpty()); i++) {
            cores[i] = pegarProcessoNaFilaCorreta();
            cores[i].setEstado(Estado.EXECUTANDO.getValor());
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

    private Processo pegarProcessoNaFilaCorreta() {
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

    private void decrementarTempoRestanteProcessoExecutando() {
        for (Processo processo : cores) {
            if (processo != null)
                processo.decrementarTempoEQuantumRestante();
        }
    }

    private void mudarProcessosDeFila() {
        for (int i = 0; i < cores.length; i++) {
            Processo processoAtual = cores[i];

            if (processoAtual != null) {
                if (processoAtual.getEstado() == Estado.FINALIZADO.getValor()) {
                    finalizados.add(processoAtual);
                    cores[i] = null;
                } else if (processoAtual.getEstado() == Estado.APTO.getValor()) {
                    alocarProcessoByPrioridade(processoAtual);
                    cores[i] = null;
                }
            }
        }

        for (int i = 0; i < cores.length; i++) {
            Processo processoAtual = cores[i];

            if (processoAtual == null && (!P0.isEmpty() || !P1.isEmpty() || !P2.isEmpty() || !P3.isEmpty())) {
                cores[i] = pegarProcessoNaFilaCorreta();
                cores[i].setEstado(Estado.EXECUTANDO.getValor());
            }
        }

    }

    public void desligarAlgoritmo() {
        boolean coreIsEmpty = true;
        for (int i = 0; i < cores.length; i++) {
            Processo processoAtual = cores[i];

            if (processoAtual != null) {
                coreIsEmpty = false;
            }
        }

        if (coreIsEmpty && P0.isEmpty() && P1.isEmpty() && P2.isEmpty() && P3.isEmpty()) {
            Constantes.RR_IS_RUNNING = false;
        }
    }

    @Override
    public void run() {
        while (Constantes.RR_IS_RUNNING) {
            try {
                print();
                Thread.sleep(1000);
                decrementarTempoRestanteProcessoExecutando();
                mudarProcessosDeFila();
                desligarAlgoritmo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void print() {
        System.out.println();
        System.out.println("**********************");
        System.out.println("--------CORES--------");
        for (Processo processo : cores) {
            if (processo != null)
                System.out.println(processo.toString());
        }
        System.out.println("--------P0--------");
        for (Processo processo : P0) {
            System.out.println(processo.toString());
        }
        System.out.println("-----P1-----");
        for (Processo processo : P1) {
            System.out.println(processo.toString());
        }
        System.out.println("--------P2--------");
        for (Processo processo : P2) {
            System.out.println(processo.toString());
        }
        System.out.println("--------P3--------");
        for (Processo processo : P3) {
            System.out.println(processo.toString());
        }
        System.out.println("--------FINALIZADOS--------");
        for (Processo processo : finalizados) {
            System.out.println(processo.toString());
        }
        System.out.println("**********************");
        System.out.println();
    }
}
