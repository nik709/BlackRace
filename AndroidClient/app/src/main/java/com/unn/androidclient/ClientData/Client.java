package com.unn.androidclient.ClientData;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 14.05.2017.
 */

public class Client {
    private Socket socket;

    public void run(){
        try {
            socket = new Socket("10.0.2.2", 1789);
            System.out.println("Successful");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
