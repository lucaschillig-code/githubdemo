package com.example.wishsolitaire;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class NytSpil {

    public static int MÆNGDE_AF_KORT = 32;
    private static Circle retryKnap;   // knappen der altid er der under spillet

    public static void minusTo() {
        MÆNGDE_AF_KORT = MÆNGDE_AF_KORT - 2;
    }

    // Kaldes én gang når spillet starter
    public static void retry(Pane pane) {
        Tid.start(pane);

        retryKnap = new Circle(50, 40, 20);
        Image reload = new Image(NytSpil.class.getResource("reload.png").toString());
        retryKnap.setFill(new ImagePattern(reload));
        retryKnap.setOnMousePressed(e -> {
            Kort.nulstil();
            Bunke.opretKort(pane);
            MÆNGDE_AF_KORT = 32;
            Tid.start(pane);

        });
        pane.getChildren().add(retryKnap);
    }

    public static void ligeNul(Pane pane) {
        if (MÆNGDE_AF_KORT != 30) return;
        Tid.gem();
        pane.getChildren().remove(retryKnap);   // væk under win-skærmen

        YouWin youWin = new YouWin();
        pane.getChildren().add(youWin);
        Label tider = new Label("Tid: " + Tid.sidste() + " s   Bedste: " + Tid.bedste() + " s");
        tider.setLayoutX(640);
        tider.setLayoutY(30);
        tider.setStyle("-fx-font-size: 3em; -fx-text-fill: #FFD700;");
        pane.getChildren().add(tider);

        Button button = new Button("Play again");
        button.setLayoutX(710);
        button.setLayoutY(740);
        button.setMinSize(175, 75);
        button.setStyle(
                "-fx-background-color: #B22222; " +
                        "-fx-border-color: #FFD700; " +
                        "-fx-border-width: 3px; " +
                        "-fx-font-size: 2em; " +
                        "-fx-text-fill: #FFD700;");

        Circle circle = new Circle(670, 775, 30);
        Image reload = new Image(NytSpil.class.getResource("reload.png").toString());
        circle.setFill(new ImagePattern(reload));

        button.setOnAction(e -> {
            pane.getChildren().removeAll(youWin, button, circle, tider);
            pane.getChildren().add(retryKnap);   // tilbage igen
            Bunke.opretKort(pane);
            MÆNGDE_AF_KORT = 32;
            Tid.start(pane);

        });

        circle.setOnMousePressed(e -> {
            pane.getChildren().removeAll(youWin, button, circle, tider);
            pane.getChildren().add(retryKnap);
            Bunke.opretsamme(pane);
            MÆNGDE_AF_KORT = 32;
            Tid.start(pane);

        });

        pane.getChildren().addAll(button, circle);
    }
}