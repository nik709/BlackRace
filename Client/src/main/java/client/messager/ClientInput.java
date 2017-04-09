package client.messager;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 09.04.2017.
 */
public class ClientInput extends Thread {

    private Socket socket;

    public ClientInput(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        while(!socket.isClosed()){
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());

                if (in.available()>0){
                    String string = in.readUTF();

                    System.out.println(string);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
