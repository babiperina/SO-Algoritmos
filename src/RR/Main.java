package RR;

import RR.algoritmo.Rr;

public class Main {

    public static void main(String[] args) {
        Rr rr = new Rr(3, 10, 1);
        new Thread(rr).start();
    }
}
