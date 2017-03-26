/**
 * Created by Никита & Вагик
 */

package client;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import java.io.*;

public class Player extends Thread {
    private Logger logger = Logger.getLogger("Logs");

    private final ImageView car;
    private final String playerName;
    private int MAX_RIGHT,MAX_LEFT;
    private Pane pane2;
    private Pane pane3;
    private Pane mainPane;
    private int speed;
    public int distance;
    private int PlayerNum;
    private Integer clientNumber = 0;

    String []images = new String[12];

    File file = new File(getClass().getResource("/music/NFS.wav").getFile()); //new File("C:\\Users\\DDD\\Documents\\GitHub\\BlackRaceFX\\src\\resource\\Music\\NFS.wav");
    Sound music = new Sound(file);

    public Player(ImageView car, String playerName, Pane pane2, Pane pane3, Pane mainPane,int num){
        this.car = car;
        this.playerName = playerName;
        this.speed = 25;
        this.pane2 = pane2;
        this.pane3 = pane3;
        this.mainPane = mainPane;
        this.PlayerNum=num;
    }

    @Override
    public void run() {
        images[0] = "-fx-image: url(\"/images/Player1.png\");";
        images[1] = "-fx-image: url(\"/images/Player1_Left.png\");";
        images[2] = "-fx-image: url(\"/images/Player1_Right.png\");";

        images[3] = "-fx-image: url(\"/images/Player2.png\");";
        images[4] = "-fx-image: url(\"/images/Player2_Left.png\");";
        images[5] = "-fx-image: url(\"/images/Player2_Right.png\");";

        images[6] = "-fx-image: url(\"/images/Player3.png\");";
        images[7] = "-fx-image: url(\"/images/Player3_Left.png\");";
        images[8] = "-fx-image: url(\"/images/Player3_Right.png\");";

        images[9] = "-fx-image: url(\"/images/Player4.png\");";
        images[10] = "-fx-image: url(\"/images/Player4_Left.png\");";
        images[11] = "-fx-image: url(\"/images/Player4_Right.png\");";

        car.setStyle(images[PlayerNum * 3]);
        car.setLayoutX(70 + PlayerNum * 175 + PlayerNum * 3);


        //Максимальное смещение игрока в стороны------
        MAX_RIGHT = (int) car.getLayoutX() + 90;
        MAX_LEFT = (int) car.getLayoutX();
        //--------------------------------------------

        //TODO add Client Number
        if(PlayerNum == clientNumber)
        {
            music.play();
            while (true) {
                distance += speed / 10;
                if (speed > 35)
                    speed = 35;
                else if (speed < 15)
                    speed = 15;

                //Движение трассы--------------------------
                pane2.setLayoutY(pane2.getLayoutY() + speed);
                pane3.setLayoutY(pane3.getLayoutY() + speed);

                if (pane2.getLayoutY() > 600)
                    pane2.setLayoutY(-600);

                if (pane3.getLayoutY() > 600)
                    pane3.setLayoutY(-600);
                //-----------------------------------------


                //Обработчик нажатия на клавиши------------
                mainPane.setOnKeyPressed((event) -> {
                    if (event.getCode() == KeyCode.LEFT) {
                        while (car.getLayoutX() > MAX_LEFT) {
                            car.setLayoutX(car.getLayoutX() - 15);
                            car.setStyle(images[PlayerNum * 3 + 1]);
                            car.setFitHeight(104);
                            car.setFitWidth(57);
                        }
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        while (car.getLayoutX() < MAX_RIGHT) {
                            car.setLayoutX(car.getLayoutX() + 15);
                            car.setStyle(images[PlayerNum * 3 + 2]);
                            car.setFitHeight(104);
                            car.setFitWidth(57);
                        }
                    }

                    if (event.getCode() == KeyCode.ESCAPE) {
                        this.stop();
                        System.exit(0);
                    }
                });

                mainPane.setOnKeyReleased((event) -> {
                    if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
                        car.setStyle(images[PlayerNum * 3]);
                        car.setFitHeight(102);
                        car.setFitWidth(48);
                    }

                });
                //-------------------------------------------
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getPlayerName() {
        return playerName;
    }
}
