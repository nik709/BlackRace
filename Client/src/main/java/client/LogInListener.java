package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Никита on 12.04.2017.
 */
public class LogInListener {

    public static boolean checkUser(Socket socket, String userName, String password) {
        boolean result = false;
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            StringBuilder sb = new StringBuilder();
            sb.append("$");
            sb.append(userName);
            sb.append("$");
            sb.append(password);

            out.writeUTF(sb.toString());
            out.flush();

            while (true){
                DataInputStream in = new DataInputStream(socket.getInputStream());

                if (in.available() > 0){
                    String string = in.readUTF();

                    if (Boolean.valueOf(string)){
                        result = true;
                    }
                    System.out.println(result);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
