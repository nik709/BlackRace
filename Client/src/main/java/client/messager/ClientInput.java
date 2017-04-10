package client.messager;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

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
                    StringTokenizer st = new StringTokenizer(string);

                    if (st.hasMoreTokens()){
                        Data.addData(Integer.valueOf(st.nextToken("\r\n")), st.nextToken("\r\n"));
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
