package client;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by Вагик on 20.03.2017.
 */
public class Barrier extends Thread{

    private final ImageView img;
    private final int speed;

    public Barrier(ImageView img, int speed){
        this.img = img;
        this.speed = speed;
    }
}
