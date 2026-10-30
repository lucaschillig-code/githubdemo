package com.example.wishsolitaire;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Kort extends ImageView {

    public static final double W = 168;
    public static final double MELLEMRUM = 18;
    public static final double RAEKKE_AFSTAND = 66;

    private static Kort valgt = null;   // det markerede kort

    private final DropShadow kant = new DropShadow(10, Color.GOLD);
    { kant.setSpread(0.8); }
    private final int nr;
    private final int kolonne;
    private boolean blevTrukket;
    private double offSetX, offSetY;

    public Kort(int kolonne, int raekke, int nr) {
        this.nr = nr;
        this.kolonne = kolonne;

        setImage(new Image(getClass().getResource("c0" + nr + ".png").toString()));
        setFitWidth(W);
        setPreserveRatio(true);
        setX(30 + kolonne * (W + MELLEMRUM));
        setY(70 + raekke * RAEKKE_AFSTAND);

        setOnMousePressed(e -> {
            blevTrukket = false;
            if (erOverst()) {
                setEffect(kant);
                offSetX = e.getSceneX() - getTranslateX();
                offSetY = e.getSceneY() - getTranslateY();
                setViewOrder(-1);
                hop();
            }
        });

        setOnMouseDragged(e -> {
            if (erOverst()) {
                blevTrukket = true;
                setTranslateX(e.getSceneX() - offSetX);
                setTranslateY(e.getSceneY() - offSetY);
            }
        });

        setOnMouseReleased(e -> {
            if (!erOverst()) return;
            if (blevTrukket) {
                slip();
            } else {
                klik();
            }

        });
    }

    private void hop() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), this);
        tt.setByX(+5);
        tt.setByY(-5);
        tt.setAutoReverse(true);
        tt.setCycleCount(2);
        tt.play();
    }

    // Almindeligt klik: vælg, fravælg eller lav par med det valgte kort
    private void klik() {
        setViewOrder(0);
        if (valgt == this) {
            setEffect(null);
            valgt = null;
        } else if (valgt != null && passer(valgt)) {
            valgt.fjernPar(this);
        } else {
            if (valgt != null) valgt.setEffect(null);
            valgt = this;
            setEffect(kant);
        }
    }

    // Efter træk: ligger kortet på et øverste kort, der passer?
    private void slip() {
        setEffect(valgt == this ? kant : null);
        for (int kol = 0; kol < Bunke.KOLONNER; kol++) {
            if (Bunke.antal[kol] == 0) continue;
            Kort andet = Bunke.listeAfKort[kol][Bunke.antal[kol] - 1];
            if (andet != this && passer(andet)
                    && getBoundsInParent().intersects(andet.getBoundsInParent())) {
                fjernPar(andet);
                return;
            }
        }
        tilbage();
    }

    private boolean passer(Kort andet) {
        return nr % 8 == andet.nr % 8;
    }

    private boolean erOverst() {
        int n = Bunke.antal[kolonne];
        return n > 0 && Bunke.listeAfKort[kolonne][n - 1] == this;
    }

    private void fjernPar(Kort andet) {
        Pane pane = (Pane) getParent();
        setViewOrder(-1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), this);
        tt.setToX(andet.getX() + andet.getTranslateX() - getX());
        tt.setToY(andet.getY() + andet.getTranslateY() - getY());
        tt.setOnFinished(e -> {
            if (valgt == this || valgt == andet) valgt = null;
            pane.getChildren().removeAll(this, andet);
            Bunke.fjernToppen(kolonne);
            Bunke.fjernToppen(andet.kolonne);
            NytSpil.minusTo();
            NytSpil.ligeNul(pane);
        });
        tt.play();
    }

    private void tilbage() {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), this);
        tt.setToX(0);
        tt.setToY(0);
        tt.setInterpolator(Interpolator.EASE_BOTH);
        tt.setOnFinished(e -> setViewOrder(0));
        tt.play();
    }

    public static void nulstil() {
        valgt = null;
    }
}