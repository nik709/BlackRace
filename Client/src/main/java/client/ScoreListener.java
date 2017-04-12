package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 12.04.2017.
 */
public class ScoreListener {

    public static void sendScore(String userName, String score) {
        try {
            Socket socket = new Socket(ClientConstants.SERVER_ADDRESS, ClientConstants.PORT_NUMBER);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            StringBuilder sb = new StringBuilder();
            sb.append(":");
            sb.append(score);
            sb.append(":");
            sb.append(userName);

            out.writeUTF(sb.toString());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
