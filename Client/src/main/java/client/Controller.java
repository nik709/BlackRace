package client;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Controller {

    public Pane pain2;
    public Pane pain3;
    public Pane mainPane;
    public ImageView testCar;
    public ImageView testCar2;
    public ImageView testCar3;
    public ImageView testCar4;

    public int PlayersQuant = 4;

    Player[]player = new Player[PlayersQuant];
    String []PlayersNames = new String[PlayersQuant];
    ImageView []images = new ImageView[4];

    public void begin(ActionEvent actionEvent) {

        for(int i=0; i<PlayersQuant; i++)
            PlayersNames[i]="Player"+i;

        images[0]=testCar;
        images[1]=testCar2;
        images[2]=testCar3;
        images[3]=testCar4;

        for(int i=0; i<PlayersQuant; i++) {
            player[i] = new Player(images[i], PlayersNames[i], pain2, pain3, mainPane, i);
            player[i].start();
        }

    }

}
