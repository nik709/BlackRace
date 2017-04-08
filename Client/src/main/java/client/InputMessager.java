package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 02.04.2017.
 */
public class InputMessager extends Thread {

    private Socket socket;

    public InputMessager(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        while (true) {
            try {
                DataInputStream is = new DataInputStream(socket.getInputStream());
                if (is != null) {
                    Logger log = Logger.getLogger(this.getClass().getName());
                    log.log(Level.INFO, "Message: " + is.readUTF());
                }

                //TODO coordinates

            } catch (IOException e) {
                Logger log = Logger.getLogger(this.getClass().getName());
                log.log(Level.INFO, "Socket for input has been closed");
            }
        }
    }
}
