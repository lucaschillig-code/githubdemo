package com.example.wishsolitaire;

import javafx.scene.layout.Pane;
import java.security.SecureRandom;

public class Bunke {

    public static final int KOLONNER = 8;
    public static final int RAEKKER = 4;

    // listeAfKort[kolonne][plads]: plads 0 er nederst
    public static final Kort[][] listeAfKort = new Kort[KOLONNER][RAEKKER];
    public static final int[] antal = new int[KOLONNER];   // kort i hver kolonne

    private static final SecureRandom random = new SecureRandom();
    private static int[] sidsteRaekkefolge;

    // Nyt spil: bland og del ud
    public static void opretKort(Pane pane) {
        int[] numre = new int[KOLONNER * RAEKKER];
        for (int i = 0; i < numre.length; i++) {
            numre[i] = i + 1;
        }
        for (int i = 0; i < numre.length; i++) {
            int j = random.nextInt(numre.length);
            int temp = numre[i];
            numre[i] = numre[j];
            numre[j] = temp;
        }
        sidsteRaekkefolge = numre;
        opretFra(pane);
    }

    // Samme spil igen: ingen blanding
    public static void opretsamme(Pane pane) {
        opretFra(pane);
    }

    private static void opretFra(Pane pane) {
        pane.getChildren().removeIf(n -> n instanceof Kort);
        int plads = 0;
        for (int kol = 0; kol < KOLONNER; kol++) {
            antal[kol] = 0;
        }
        for (int raekke = 0; raekke < RAEKKER; raekke++) {
            for (int kol = 0; kol < KOLONNER; kol++) {
                Kort kort = new Kort(kol, raekke, sidsteRaekkefolge[plads]);
                pane.getChildren().add(kort);
                listeAfKort[kol][antal[kol]] = kort;
                antal[kol]++;
                plads++;
            }
        }
    }

    public static void fjernToppen(int kol) {
        antal[kol]--;
        listeAfKort[kol][antal[kol]] = null;
    }
}