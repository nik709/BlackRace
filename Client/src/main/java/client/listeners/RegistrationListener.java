package client.listeners;

import client.ClientConstants;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 16.04.2017.
 */
public class RegistrationListener {

    public static void sendRequestForRegistrate(String userName, String password){
        try {
            Socket socket = new Socket(ClientConstants.SERVER_ADDRESS, ClientConstants.PORT_NUMBER);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            StringBuilder sb = new StringBuilder();
            sb.append("::");
            sb.append(userName);
            sb.append("::");
            sb.append(password);

            out.writeUTF(sb.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
