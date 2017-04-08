package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 08.04.2017.
 */
public class OutputMessager extends Thread {

    private Socket clientSocket;
    String message;

    public OutputMessager(Socket clientSocket, String message){
        this.clientSocket = clientSocket;
        this.message = message;
    }

    @Override
    public void run(){
        while(true){
            try {
                DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());

                os.writeUTF(message);
                os.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
