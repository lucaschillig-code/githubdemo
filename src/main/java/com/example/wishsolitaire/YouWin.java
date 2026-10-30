package com.example.wishsolitaire;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class YouWin extends ImageView {

    public YouWin() {
        super();
        setImage (new Image(YouWin.class.getResource("YouWin.png").toString()));
        setX(0);
        setY(0);
    }
}
