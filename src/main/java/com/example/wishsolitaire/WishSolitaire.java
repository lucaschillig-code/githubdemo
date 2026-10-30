package com.example.wishsolitaire;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WishSolitaire extends Application {
    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        pane.getChildren().add(new Background());
        Bunke.opretKort(pane);
        NytSpil.retry(pane);
        Tid.start(pane);

        Scene scene = new Scene(pane, 1535, 1080);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}