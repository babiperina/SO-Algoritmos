package RR.algoritmo;

import SJF.model.Processo;

import java.util.ArrayList;

public class Rr implements Runnable {

    private int quantum;

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

    }

    @Override
    public void run() {

    }
}
