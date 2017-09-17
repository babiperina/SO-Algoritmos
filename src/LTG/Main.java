package LTG;

import LTG.algoritmo.Ltg;

import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
        Ltg ltg = new Ltg(3, 20);
        new Thread(ltg).start();
    }
}
