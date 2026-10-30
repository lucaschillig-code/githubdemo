package com.example.wishsolitaire;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Tid {

    private static int sekunder;
    private static final Label tidTekst = new Label("0");
    private static Timeline timer;
    private static int bedste = -1;   // -1 = ingen endnu
    private static int sidste = -1;
    public static int bedste() { return bedste; }
    public static int sidste() { return sidste; }

    public static void start(Pane pane) {
        if (timer != null) timer.stop();
        sekunder = 0;
        tidTekst.setText("0");
        tidTekst.setLayoutX(200);
        tidTekst.setLayoutY(25);
        tidTekst.setStyle("-fx-font-size: 2em; -fx-text-fill: #FFD700;");
        if (!pane.getChildren().contains(tidTekst)) {
            pane.getChildren().add(tidTekst);
        }

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            sekunder++;
            tidTekst.setText("" + sekunder);
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }


    // Kaldes når spillet er vundet
    public static void gem() {
        timer.stop();
        sidste = sekunder;
        if (bedste == -1 || sekunder < bedste) {
            bedste = sekunder;
        }
    }

    public static void stop() {
        if (timer != null) timer.stop();
    }

    public static int sekunder() {
        return sekunder;
    }
}