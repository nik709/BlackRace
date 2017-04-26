package client;

import client.listeners.QuantityListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;
import client.messager.Data;

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

        //Map<Integer, Integer> map = QuantityListener.sendRequest();
        Map<Integer, Integer> map = Data.quantity;
        Integer number=0;

        if(map!=null && !map.isEmpty()) {
            PlayersCount = map.keySet().iterator().next();
            number = map.get(PlayersCount);
        }
        QuantityListener.printMap();

        System.out.println("Players: " + PlayersCount);

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

        System.out.print("Number ");
        System.out.println(number);
        System.out.print("PlayersCount ");
        System.out.println(PlayersCount);

        for (int i = 0; i < PlayersCount; i++) {
            player[i] = new Player(images[i], police1, police2, police3, police4, police5, police6, police7, police8,
                    PlayersNames[i], pain2, pain3, mainPane, PlayersCount, i, number);
            player[i].start();
        }
    }
}