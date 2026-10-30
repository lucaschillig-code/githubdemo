package com.example.wishsolitaire;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background extends ImageView {

    public Background() {
        super();                          // tom ImageView
        setImage (new Image(Background.class.getResource("background.png").toString()));
        setX(0);
        setY(0);
    }
}