package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Created by Никита on 12.04.2017.
 */
public class RegistrationListener {

    public static void registration(Socket socket, String userName, String password1, String password2){
        try {
            if (password1.equals(password2)){
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                StringBuilder sb = new StringBuilder();
                sb.append("#");
                sb.append(userName);
                sb.append("#");
                sb.append(password1);

                out.writeUTF(sb.toString());
                out.flush();
            }
            else{
                //TODO tell about the mistake
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
