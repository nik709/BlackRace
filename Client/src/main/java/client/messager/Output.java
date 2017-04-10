package client.messager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 09.04.2017.
 */
public class Output {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Socket socket;

    Integer playerNumber;
    Double X;

    public Output(Socket socket, Integer playerNumber, Double X){
        this.socket = socket;
        this.playerNumber = playerNumber;
        this.X = X;
    }

    public void send(){
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                StringBuilder sb = new StringBuilder();
                sb.append(playerNumber);
                sb.append("\r\n");
                sb.append(X);
                sb.append("\r\n");

                out.writeUTF(sb.toString());
                out.flush();

            } catch (IOException e) {
                logger.log(Level.INFO, "Output of client messager: ");
                e.printStackTrace();
            }
    }


}
