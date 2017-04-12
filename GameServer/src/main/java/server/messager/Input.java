package server.messager;

import server.DataBase;
import server.GameServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
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

                    if (string.startsWith("#")){
                        StringTokenizer st = new StringTokenizer(string);
                        String userName = "";
                        String userPassword = "";
                        if (st.hasMoreTokens())
                            userName = st.nextToken("#");
                        if (st.hasMoreTokens())
                            userPassword = st.nextToken("#");

                        DataBase db = DataBase.getInstance();
                        db.insertNewUser(userName, userPassword);
                    }
                    else if (string.startsWith("$")){
                        StringTokenizer st = new StringTokenizer(string);
                        String userName = "";
                        String userPassword = "";
                        if (st.hasMoreTokens())
                            userName = st.nextToken("$");
                        if (st.hasMoreTokens())
                            userPassword = st.nextToken("$");

                        DataBase db = DataBase.getInstance();
                        Boolean allowed = db.checkUser(userName, userPassword);

                        DataOutputStream respone = new DataOutputStream(socket.getOutputStream());
                        respone.writeUTF(allowed.toString());
                        respone.flush();
                    }
                    else {
                        List<Socket> sockets = new ArrayList<>();
                        List<Socket> clients = GameServer.getClients();
                        for (Socket socket: clients){
                            if (!socket.isClosed())
                                sockets.add(socket);
                        }
                        for (Socket socket : sockets) {
                            DataOutputStream response = null;
                            try {
                                response = new DataOutputStream(socket.getOutputStream());
                                response.writeUTF(string);
                                response.flush();
                            } catch (IOException e) {
                                System.out.println("WriteUTF error: " + string);
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
