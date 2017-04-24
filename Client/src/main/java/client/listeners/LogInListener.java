package client.listeners;

import client.ClientConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 20.04.2017.
 */
public class LogInListener  {

    public static boolean checkUser(String userName, String password){
        Socket socket = null;
        try {
            socket = new Socket(ClientConstants.SERVER_ADDRESS, ClientConstants.PORT_NUMBER);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            StringBuilder sb = new StringBuilder();
            sb.append("??");
            sb.append(userName);
            sb.append("??");
            sb.append(password);

            out.writeUTF(sb.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean result = false;

        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String string  = in.readUTF();

            if ("true".equals(string)){
                result = true;
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
