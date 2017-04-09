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

    Integer clientNumber;
    Double X;
    Boolean isAlive;

    public Output(Socket socket, Integer clientNumber, Double X, Boolean isAlive){
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.X = X;
        this.isAlive = isAlive;
    }

    public void send(){
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                StringBuilder sb = new StringBuilder();
                sb.append(clientNumber);
                sb.append("\r\n");
                sb.append(X);
                sb.append("\r\n");
                sb.append(isAlive);

                out.writeUTF(sb.toString());
                out.flush();

            } catch (IOException e) {
                logger.log(Level.INFO, "Output of client messager: ");
                e.printStackTrace();
            }
    }


}
