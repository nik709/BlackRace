package server.GUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import server.DataBase;
import server.GameServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public TextField userID;
    public TextField message;
    public ListView<String> serverView;
    public ListView<String> usersView;

    GameServer gameServer;
    DataBase dataBase;

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
        dataBase = DataBase.getInstance();
        dataBase.setServerView(serverView);
        dataBase.printInfo();
    }

    public void findUser(ActionEvent actionEvent) {
        String userID = this.userID.getText();
        if (userID != null && !"".equals(userID)) {
            HashMap<String, Integer> users = dataBase.findUserStatistic(userID);
            Iterator it = users.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                usersView.getItems().add("User " + pair.getKey() + " has score: " + pair.getValue());
                it.remove();
            }
        }
        else{
            usersView.getItems().add("Incorrect user id");
        }
    }

    public void sendMessage(ActionEvent actionEvent) {
        String message = this.message.getText();
        String ls = System.getProperty("line.separator");

        StringBuilder builder = new StringBuilder();
        builder.append(message);
        builder.append(ls);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<Socket> clients = gameServer.getClients();
                for (Socket client: clients) {
                    DataOutputStream response = null;
                    try {
                        response = new DataOutputStream(client.getOutputStream());
                        response.writeUTF(message);
                        response.flush();
                    } catch (IOException e) {
                        serverView.getItems().add("Can't send the message");
                    }
                }
            }
        });
    }

    public void resetDB(ActionEvent actionEvent) {
        dataBase.resetDataBase();
    }
}
