package client.listeners;

import client.ClientConstants;
import client.messager.Data;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 20.04.2017.
 */
public class ScoreListener {

    public static void sendScore(Integer score){
        try {
            Socket socket = new Socket(ClientConstants.SERVER_ADDRESS, ClientConstants.PORT_NUMBER);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            StringBuilder sb = new StringBuilder();
            sb.append("!!");
            sb.append(Data.getName());
            sb.append("!!");
            sb.append(score);

            out.writeUTF(sb.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
