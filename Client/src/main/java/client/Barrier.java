/**
 * Created by Никита & Вагик
 */

package client;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import java.io.*;

public class Barrier extends Thread {

    private final ImageView enemy1;
    private final ImageView enemy2;

    private int speed=15;
    private  int num;

    public Barrier(ImageView police1,ImageView police2,int num){
        this.enemy1 = police1;
        this.enemy2 = police2;
        this.num = num;
    }

    @Override
    public void run() {
        int leftLane = 70 + num * 175 + num * 3;
        int rightLane = 70 + num * 175 + num * 3 + 90;

        //--------------------Начальное положение-------------
        enemy1.setLayoutX(leftLane);
        enemy1.setLayoutY(-150);
        enemy2.setLayoutX(rightLane);
        enemy2.setLayoutY(-300);
        //----------------------------------------------------

            while (true) {
                enemy1.setLayoutY(enemy1.getLayoutY() + speed);
                enemy2.setLayoutY(enemy2.getLayoutY() + speed);

                if(enemy1.getLayoutY()>600) {
                    enemy1.setLayoutY(-150);
                }

                if(enemy2.getLayoutY()>600) {
                    enemy2.setLayoutY(-300);
                }

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }



    }

}
