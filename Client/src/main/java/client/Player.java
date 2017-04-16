/**
 * Created by Никита & Вагик
 */

package client;

import client.messager.ClientInput;
import client.messager.Data;
import client.messager.Output;
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

    private Socket socket;

    boolean alive = true;

    int enemy_speed = 15;
    String []images = new String[16];
    ImageView []enemies = new ImageView[8];

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

        images[12] = "-fx-image: url(\"/images/Player1_Broken.png\");";
        images[13] = "-fx-image: url(\"/images/Player2_Broken.png\");";
        images[14] = "-fx-image: url(\"/images/Player3_Broken.png\");";
        images[15] = "-fx-image: url(\"/images/Player4_Broken.png\");";

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
        enemies[0] = enemy1;
        enemies[1] = enemy2;
        enemies[2] = enemy3;
        enemies[3] = enemy4;
        enemies[4] = enemy5;
        enemies[5] = enemy6;
        enemies[6] = enemy7;
        enemies[7] = enemy8;

        enemies[0].setLayoutX(leftLane1);
        enemies[0].setLayoutY(-150);
        enemies[1].setLayoutX(rightLane1);
        enemies[1].setLayoutY(-300);

        enemies[2].setLayoutX(leftLane2);
        enemies[2].setLayoutY(-150);
        enemies[3].setLayoutX(rightLane2);
        enemies[3].setLayoutY(-300);

        enemies[4].setLayoutX(leftLane3);
        enemies[4].setLayoutY(-150);
        enemies[5].setLayoutX(rightLane3);
        enemies[5].setLayoutY(-300);

        enemies[6].setLayoutX(leftLane4);
        enemies[6].setLayoutY(-150);
        enemies[7].setLayoutX(rightLane4);
        enemies[7].setLayoutY(-300);

        //Максимальное смещение игрока в стороны------
        MAX_RIGHT = (int) car.getLayoutX() + 90;
        MAX_LEFT = (int) car.getLayoutX();
        //--------------------------------------------

        try {
            socket = new Socket(ClientConstants.SERVER_ADDRESS, ClientConstants.PORT_NUMBER);
            logger.log(Level.INFO, "Connection was successful");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int []left_enemy = new int[2];
        left_enemy[0] = -150;
        left_enemy[1] = -450;
        int index_l = 1;
        int index_r = 0;

        ClientInput clientInput = new ClientInput(socket);
        clientInput.start();

        while (true) {
            if (PlayerNum == clientNumber){
                if ( ((car.getLayoutY() == enemies[2*PlayerNum].getLayoutY()) && (car.getLayoutX() == enemies[2*PlayerNum].getLayoutX())) ||
                        ((car.getLayoutY() == enemies[2*PlayerNum+1].getLayoutY()) && (car.getLayoutX() == enemies[2*PlayerNum+1].getLayoutX())) ){
                    alive = false;
                    car.setStyle(images[12+PlayerNum]);
                    car.setLayoutX((PlayerNum<2)? 0 : 755);
                    car.setLayoutY((PlayerNum%2 == 1)? 200 : 420);
                    car.setFitHeight(130);
                }

                //--------------Движение препятствий-----------------
                enemy1.setLayoutY(enemy1.getLayoutY() + enemy_speed);
                enemy2.setLayoutY(enemy2.getLayoutY() + enemy_speed);

                if (enemies[0].getLayoutY() >= 600) {
                    enemies[0].setLayoutY(left_enemy[index_l]);
                    index_l++;
                    if(index_l>1)
                        index_l=0;
                }
                if (enemies[1].getLayoutY() >= 600) {
                    enemies[1].setLayoutY(left_enemy[index_r]);
                    index_r++;
                    if(index_r>1)
                        index_r=0;
                }

                if(playersCount>1) {
                    enemies[2].setLayoutY(enemies[0].getLayoutY());
                    enemies[3].setLayoutY(enemies[1].getLayoutY());
                }
                if(playersCount>2) {
                    enemies[4].setLayoutY(enemies[0].getLayoutY());
                    enemies[5].setLayoutY(enemies[1].getLayoutY());
                }
                if(playersCount>3) {
                    enemies[6].setLayoutY(enemies[0].getLayoutY());
                    enemies[7].setLayoutY(enemies[1].getLayoutY());

                }
                //--------------Движение препятствий-----------------


                if(alive)
                    distance += 1;

                if(distance==1300){
                    speed =40;
                    enemy_speed = 30;
                }
                else
                    if(distance==800){
                        speed = 35;
                        enemy_speed = 25;
                    }
                    else
                        if(distance==450) {
                            speed = 30;
                            enemy_speed = 20;
                        }
                        else
                            if(distance==150) {
                                speed = 25;
                                enemy_speed = 15;
                            }

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
                    if (event.getCode() == KeyCode.LEFT && alive) {
                        while (car.getLayoutX() > MAX_LEFT) {
                            car.setLayoutX(car.getLayoutX() - 15);
                            car.setStyle(images[PlayerNum * 3 + 1]);
                            car.setFitHeight(104);
                            car.setFitWidth(57);
                        }
                    } else if (event.getCode() == KeyCode.RIGHT && alive) {
                        while (car.getLayoutX() < MAX_RIGHT) {
                            car.setLayoutX(car.getLayoutX() + 15);
                            car.setStyle(images[PlayerNum * 3 + 2]);
                            car.setFitHeight(104);
                            car.setFitWidth(57);
                        }
                    }

                    if (event.getCode() == KeyCode.ESCAPE) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        this.stop();
                        System.exit(0);
                    }
                });

                mainPane.setOnKeyReleased((event) -> {
                    if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) && alive) {
                        car.setStyle(images[PlayerNum * 3]);
                        car.setFitHeight(102);
                        car.setFitWidth(48);
                    }

                });
                //-------------------------------------------
                StringBuilder sb = new StringBuilder();
                sb.append(car.getLayoutX());
                sb.append("\r\n");

                Output output = new Output(socket, PlayerNum, car.getLayoutX());
                output.send();

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                if(Data.getData(PlayerNum)!=null){
                    if(Double.parseDouble(Data.getData(PlayerNum))==0){
                        car.setStyle(images[12+PlayerNum]);
                        car.setFitHeight(130);
                        car.setLayoutX(0);
                        car.setLayoutY((PlayerNum%2 == 1)? 200 : 420);
                    }
                    else
                        car.setLayoutX(Double.parseDouble(Data.getData(PlayerNum)));
                }

            }

        }

    }

}