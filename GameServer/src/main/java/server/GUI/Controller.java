package server.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import server.GameServer;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public ListView<String> serverView;
    GameServer gameServer;

    Integer countPlayers = 0;


    public synchronized void startServer(ActionEvent actionEvent) {
        gameServer = new GameServer(serverView);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameServer.start();
            }
        });
    }

    public void connectToDB(ActionEvent actionEvent) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            serverView.getItems().add("\nDriver has been found");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Connection connection = null;
                    try {
                        Locale.setDefault(Locale.ENGLISH);
                        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
                        serverView.getItems().add("Connection has been created\n");
                        //Statement statement = connection.createStatement();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            logger.log(Level.INFO, "Can't find ORACLE driver");
        }
    }
}
