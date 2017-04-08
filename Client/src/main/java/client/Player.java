/**
 * Created by Никита & Вагик
 */

package client;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.Socket;
import java.util.logging.Level;
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
    private int clientNumber = 0;
    private int playersCount = 0;

    private final ImageView enemy1;
    private final ImageView enemy2;
    private final ImageView enemy3;
    private final ImageView enemy4;
    private final ImageView enemy5;
    private final ImageView enemy6;
    private final ImageView enemy7;
    private final ImageView enemy8;

    Socket socket;

    int enemy_speed = 15;
    String []images = new String[12];

  //   File file = new File(getClass().getResource("/music/NFS.wav").getFile()); //new File("C:\\Users\\DDD\\Documents\\GitHub\\BlackRaceFX\\src\\resource\\Music\\NFS.wav");
  //  Sound music = new Sound(file);

    public Player(ImageView car,ImageView police1,ImageView police2,ImageView police3,ImageView police4,
                  ImageView police5,ImageView police6,ImageView police7,ImageView police8,
                  String playerName, Pane pane2, Pane pane3, Pane mainPane,int playersCount,int num){
        this.car = car;
        this.playerName = playerName;

        this.enemy1 = police1;
        this.enemy2 = police2;
        this.enemy3 = police3;
        this.enemy4 = police4;
        this.enemy5 = police5;
        this.enemy6 = police6;
        this.enemy7 = police7;
        this.enemy8 = police8;

        this.speed = 25;
        this.pane2 = pane2;
        this.pane3 = pane3;
        this.mainPane = mainPane;
        this.PlayerNum=num;
        this.playersCount=playersCount;

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


        int leftLane1 = 70;
        int rightLane1 = 160;

        int leftLane2 = 248;
        int rightLane2 = 338;

        int leftLane3 = 426;
        int rightLane3 = 516;

        int leftLane4 = 604;
        int rightLane4 = 694;

        //--------------------Начальное положение-------------
        enemy1.setLayoutX(leftLane1);
        enemy1.setLayoutY(-150);
        enemy2.setLayoutX(rightLane1);
        enemy2.setLayoutY(-300);

        enemy3.setLayoutX(leftLane2);
        enemy3.setLayoutY(-150);
        enemy4.setLayoutX(rightLane2);
        enemy4.setLayoutY(-300);

        enemy5.setLayoutX(leftLane3);
        enemy5.setLayoutY(-150);
        enemy6.setLayoutX(rightLane3);
        enemy6.setLayoutY(-300);

        enemy7.setLayoutX(leftLane4);
        enemy7.setLayoutY(-150);
        enemy8.setLayoutX(rightLane4);
        enemy8.setLayoutY(-300);

        //Максимальное смещение игрока в стороны------
        MAX_RIGHT = (int) car.getLayoutX() + 90;
        MAX_LEFT = (int) car.getLayoutX();
        //--------------------------------------------

        try {
            socket = new Socket(ClientConstants.SERVER_ADDRESS, ClientConstants.PORT_NUMBER);
            logger.log(Level.INFO, "Connection was successful");

            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            String message = String.valueOf("PLAYER");
            os.writeUTF(message);
            os.flush();

            OutputMessager outputMessager = new OutputMessager(socket);
            outputMessager.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //TODO add Client Number
        while (true) {
            if (PlayerNum == clientNumber){
                //--------------Движение препятствий-----------------

                enemy1.setLayoutY(enemy1.getLayoutY() + enemy_speed);
                enemy2.setLayoutY(enemy2.getLayoutY() + enemy_speed);
                if (enemy1.getLayoutY() > 600)
                    enemy1.setLayoutY(-150);
                if (enemy2.getLayoutY() > 600)
                    enemy2.setLayoutY(-300);

                if(playersCount>1) {
                    enemy3.setLayoutY(enemy3.getLayoutY() + enemy_speed);
                    enemy4.setLayoutY(enemy4.getLayoutY() + enemy_speed);
                    if (enemy3.getLayoutY() > 600)
                        enemy3.setLayoutY(-150);
                    if (enemy4.getLayoutY() > 600)
                        enemy4.setLayoutY(-300);
                }
                if(playersCount>2) {
                    enemy5.setLayoutY(enemy5.getLayoutY() + enemy_speed);
                    enemy6.setLayoutY(enemy6.getLayoutY() + enemy_speed);
                    if (enemy5.getLayoutY() > 600)
                        enemy5.setLayoutY(-150);
                    if (enemy6.getLayoutY() > 600)
                        enemy6.setLayoutY(-300);
                }
                if(playersCount>3) {
                    enemy7.setLayoutY(enemy7.getLayoutY() + enemy_speed);
                    enemy8.setLayoutY(enemy8.getLayoutY() + enemy_speed);
                    if (enemy7.getLayoutY() > 600)
                        enemy7.setLayoutY(-150);
                    if (enemy8.getLayoutY() > 600)
                        enemy8.setLayoutY(-300);
                }
                //--------------Движение препятствий-----------------


                //   music.play();
                distance += speed / 10;

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
                        //this.stop();
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
            else
            {
                //Получение координат других пользователей
            }

        }




    }

    public String getPlayerName() {
        return playerName;
    }
}
