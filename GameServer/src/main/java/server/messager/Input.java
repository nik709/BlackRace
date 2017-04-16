package server.messager;

import server.DataBase;
import server.GameServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 09.04.2017.
 */
public class Input extends Thread {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Socket socket;

    public Input(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){

        while(!socket.isClosed()){
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());

                if (in.available() > 0) {
                    String string = in.readUTF();

                    if (string.startsWith("::")){

                        StringTokenizer st = new StringTokenizer(string);
                        String userName = "";
                        String password = "";

                        if (st.hasMoreTokens())
                            userName = st.nextToken("::");
                        if (st.hasMoreTokens())
                            password = st.nextToken("::");

                        DataBase db = DataBase.getInstance();
                        db.insertUser(userName, password);

                        socket.close();
                    }
                    else {
                        List<Socket> sockets = GameServer.getClients();
                        for (Socket socket : sockets) {
                            DataOutputStream response = null;
                            try {
                                response = new DataOutputStream(socket.getOutputStream());
                                response.writeUTF(string);
                                response.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            } catch (IOException e) {
                logger.log(Level.INFO, "Input of server messager: ");
                e.printStackTrace();
            }
        }

    }

}
