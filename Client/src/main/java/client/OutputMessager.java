package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 02.04.2017.
 */
public class OutputMessager extends Thread {

    private Socket socket;

    public OutputMessager(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        while (true) {
            try {
                DataInputStream is = new DataInputStream(socket.getInputStream());
                if (is != null) {
                    System.out.print(is.readUTF());
                }

                DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                String message = String.valueOf("Hello! I'm player " + 1);
                os.writeUTF(message);
                os.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
