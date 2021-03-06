package LTG.algoritmo;

import LTG.model.Estado;
import LTG.model.Processo;
import LTG.util.Constantes;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Ltg implements Runnable {

    Processo[] cores;
    ArrayList<Processo> aptos = new ArrayList<>();
    ArrayList<Processo> finalizados = new ArrayList<>();
    ArrayList<Processo> abortados = new ArrayList<>();

    public Ltg(int qtdeCores, int qtdeProcessosIniciais) {
        Constantes.LTG_IS_RUNNING = true;
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
        aptos.sort((p1, p2) -> p1.getDeadline().compareTo(p2.getDeadline()));
    }

    private void decrementarTempoRestanteProcessosExecutando() {
        for (Processo processo : cores) {
            if (processo != null)
                processo.decrementarTempoRestante();
        }
    }

    private void verificarDeadline() {
        Timestamp horaAtual = new Timestamp(System.currentTimeMillis());
        for (int i = 0; i < aptos.size(); i++) {
            Processo processoAtual = aptos.get(i);

            if (processoAtual.getDeadline().getTime() <= horaAtual.getTime()) {
                processoAtual.setEstado(Estado.ABORTADO.getValor());
                abortados.add(processoAtual);
                aptos.remove(i);
                i--;
            }
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
            Constantes.LTG_IS_RUNNING = false;
        }
    }

    @Override
    public void run() {
        while (Constantes.LTG_IS_RUNNING) {
            try {
                print();
                Thread.sleep(1000);
                decrementarTempoRestanteProcessosExecutando();
                verificarDeadline();
                mudarProcessosDeFila();
                desligarAlgoritmo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        print();
    }

    public void print() {
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
        System.out.println("-----ABORTADOS-----");
        for (Processo processo : abortados) {
            System.out.println(processo.toString());
        }
        System.out.println("**********************");
        System.out.println();

    }
}
