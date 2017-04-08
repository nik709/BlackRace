package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 08.04.2017.
 */
public class OutputMessager extends Thread {

    private Socket socket;

    private String message;

    public OutputMessager(Socket socket, String message){
        this.socket = socket;
        this.message = message;
    }

    @Override
    public void run(){
        while(true) {
            try {
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());

                StringBuilder sb = new StringBuilder(message);
                sb.append("\r\n");

                os.writeUTF(sb.toString());
                os.flush();

            } catch (IOException e) {
                Logger log = Logger.getLogger(this.getClass().getName());
                log.log(Level.INFO, "Socket for output has been closed");
            }
        }
    }
}
