package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 02.04.2017.
 */
public class InputMessager extends Thread {

    private Socket clientSocket;

    public InputMessager(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        while (true) {
            try {
                DataInputStream is = new DataInputStream(clientSocket.getInputStream());
                if (is != null) {
                    System.out.print(is.readUTF());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
