package SJF;

import SJF.algoritmo.Sjf;

public class Main {

    public static void main(String[] args) {
        Sjf sjf = new Sjf(2, 5);
        new Thread(sjf).start();
    }
}
