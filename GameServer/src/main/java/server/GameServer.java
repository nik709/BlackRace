package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 12.03.2017.
 */
public class GameServer extends Thread {

    private Logger logger = Logger.getLogger(getClass().getName());

    private ServerSocket serverSocket;
    private ListView<String> serverView;

    private Integer clientQuantity;

    private ArrayList<Socket> clients = new ArrayList<Socket>();

    private Worker worker;

    public GameServer(ListView<String> serverView){
        this.serverView = serverView;
        this.clientQuantity = 0;
    }

    public void startServer(){
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(ServerConstants.PORT_NUMBER);
            serverView.getItems().add(ServerConstants.STARTED_MESSAGE + ServerConstants.PORT_NUMBER);

            while (true){
                try {
                    Socket clientSocket = serverSocket.accept();
                    if (clientSocket != null) {
                        clientQuantity++;
                        clients.add(clientSocket);
                    }

                    worker = new Worker(clientSocket, serverView, clientQuantity);
                    worker.execute();

                } catch (IOException e) {
                    serverView.getItems().add(ServerConstants.FAIL_CONNECTED_MESSAGE);
                }
            }
        } catch (IOException ex){
            logger.log(Level.INFO, ServerConstants.FAIL_MESSAGE);
        }
    }

    @Override
    public void run(){
        startServer();
    }

    public ArrayList<Socket> getClients() {
        return clients;
    }
}
