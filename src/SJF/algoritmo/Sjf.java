package SJF.algoritmo;

import SJF.model.Estado;
import SJF.model.Processo;
import SJF.util.Constantes;

import java.util.ArrayList;

public class Sjf implements Runnable {


    Processo[] cores;
    ArrayList<Processo> aptos = new ArrayList<>();
    ArrayList<Processo> finalizados = new ArrayList<>();

    public Sjf(int qtdeCores, int qtdeProcessosIniciais) {
        Constantes.SJF_IS_RUNNING = true;
        cores = new Processo[qtdeCores];
        for (int i = 0; i < qtdeProcessosIniciais; i++) {
            adicionarProcesso(new Processo());
        }
        for (int i = 0; i < cores.length && !aptos.isEmpty(); i++) {
            cores[i] = aptos.remove(0);
            cores[i].setEstado(Estado.EXECUTANDO.getValor());
        }
    }

    public void adicionarProcesso(Processo processo) {
        aptos.add(processo);
        aptos.sort((p1, p2) -> p1.getDuracao().compareTo(p2.getDuracao()));
    }

    private void decrementarTempoRestanteProcessosExecutando() {
        for (Processo processo : cores) {
            if (processo != null)
                processo.decrementarTempoRestante();
        }
    }

    private void mudarProcessosDeFila() {
        for (int i = 0; i < cores.length; i++) {
            Processo processoAtual = cores[i];

            if (processoAtual != null) {
                if (processoAtual.getEstado() == Estado.FINALIZADO.getValor()) {
                    finalizados.add(processoAtual);
                    cores[i] = null;
                }
            }
        }

        for (int i = 0; i < cores.length; i++) {
            Processo processoAtual = cores[i];

            if (processoAtual == null && !aptos.isEmpty()) {
                cores[i] = aptos.remove(0);
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

        if (coreIsEmpty && aptos.isEmpty()) {
            Constantes.SJF_IS_RUNNING = false;
        }
    }

    @Override
    public void run() {
        while (Constantes.SJF_IS_RUNNING) {
            try {
                printSjf();
                Thread.sleep(2000);
                decrementarTempoRestanteProcessosExecutando();
                mudarProcessosDeFila();
                desligarAlgoritmo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printSjf() {
        System.out.println();
        System.out.println("**********************");
        System.out.println("--------CORES--------");
        for (Processo processo : cores) {
            if (processo != null)
                System.out.println(processo.toString());
        }
        System.out.println("--------APTOS--------");
        for (Processo processo : aptos) {
            System.out.println(processo.toString());
        }
        System.out.println("-----FINALIZADOS-----");
        for (Processo processo : finalizados) {
            System.out.println(processo.toString());
        }
        System.out.println("**********************");
        System.out.println();

    }
}
