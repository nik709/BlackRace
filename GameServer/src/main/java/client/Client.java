package client;

import server.ServerConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 12.03.2017.
 */
public class Client {

    private Logger logger = Logger.getLogger(getClass().getName());

    public Client(){
        try{
            Socket clientSocket = new Socket(ServerConstants.SERVER_ADDRESS, ServerConstants.PORT_NUMBER);
            logger.log(Level.INFO, "Connection to server was successful");

            StringBuilder responce = new StringBuilder();
            SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
            responce.append("Date: ");
            responce.append(df.format(new Date()));
            responce.append("\r\n");
            responce.append("Hi! I'm a new client.");
            responce.append("\r\n");

            ClientData clientData = new ClientData(1, 1.2, 2.1);

            responce.append(clientData.getData().getAttribute("Client Number"));
            responce.append("\r\n");
            responce.append(clientData.getData().getAttribute("X"));
            responce.append("\r\n");
            responce.append(clientData.getData().getAttribute("Y"));
            responce.append("\r\n");

            DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());


            os.writeUTF(responce.toString());
            os.flush();

            while (true) {
                DataInputStream is = new DataInputStream(clientSocket.getInputStream());
                if (is != null)
                    System.out.print(is.readUTF());
            }

        } catch (IOException ex){
            logger.log(Level.INFO, "Can't connect to " + ServerConstants.SERVER_ADDRESS);
            System.exit(2);
        }
    }
}
