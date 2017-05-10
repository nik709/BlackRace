package client;

import client.listeners.QuantityListener;
import client.messager.Data;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;


public class Controller {

    public Pane pain2;
    public Pane pain3;
    public Pane mainPane;

    public ImageView testCar;
    public ImageView testCar2;
    public ImageView testCar3;
    public ImageView testCar4;

    public ImageView police1, police2;
    public ImageView police3, police4;
    public ImageView police5, police6;
    public ImageView police7, police8;

    public int PlayersCount;

    public void begin(ActionEvent actionEvent) {

        Map<Integer, Integer> map = Data.quantity;


        System.out.println("Map:");
        System.out.println("    Key Set: " + map.keySet().isEmpty());

        PlayersCount = map.keySet().iterator().next();

        Integer number = map.get(PlayersCount);

        Player[] player = new Player[PlayersCount];
        String[] PlayersNames = new String[PlayersCount];
        ImageView[] images = new ImageView[4];

        for (int i = 0; i < PlayersCount; i++) {
            PlayersNames[i] = "Player" + i;
        }

        images[0] = testCar;
        images[1] = testCar2;
        images[2] = testCar3;
        images[3] = testCar4;

        for (int i = 0; i < PlayersCount; i++) {
            player[i] = new Player(images[i], police1, police2, police3, police4, police5, police6, police7, police8,
                    PlayersNames[i], pain2, pain3, mainPane, PlayersCount, i, number);
            player[i].start();
        }
    }
}