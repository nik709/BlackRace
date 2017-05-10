package server.messager;

import server.DataBase;
import server.GameServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 09.04.2017.
 */
public class Input extends Thread {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static List<Integer> quantity = new ArrayList<>();
    private static Integer maxUsers = 0;

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
                    else if (string.startsWith("??")){
                        StringTokenizer st = new StringTokenizer(string);
                        String userName = "";
                        String password = "";

                        if (st.hasMoreTokens())
                            userName = st.nextToken("??");
                        if (st.hasMoreTokens())
                            password = st.nextToken("??");

                        DataBase db = DataBase.getInstance();

                        Boolean response = db.checkUser(userName, password);

                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        out.writeUTF(response.toString());
                        out.flush();

                        if (response){
                            if (quantity.isEmpty())
                                quantity.add(0);
                            else
                                quantity.add(quantity.get(quantity.size()-1) + 1);
                        }

                        if (maxUsers < quantity.size())
                            maxUsers = quantity.size();
                        System.out.println("Players: " + maxUsers);

                        socket.close();
                    }
                    else if (string.startsWith("!!")){
                        StringTokenizer st = new StringTokenizer(string);
                        String userName = "";
                        String scoreS = "";

                        if (st.hasMoreTokens())
                            userName = st.nextToken("!!");
                        if (st.hasMoreTokens())
                            scoreS = st.nextToken("!!");

                        DataBase db = DataBase.getInstance();
                        Integer newScore = Integer.valueOf(scoreS);
                        Integer currentScore = db.getUserScore(userName);

                        if (newScore > currentScore){
                            db.updateUserScore(userName, newScore);
                        }

                        socket.close();
                    }
                    else if (string.equals("uq")){
                        DataOutputStream response = null;
                        try {
                            response = new DataOutputStream(socket.getOutputStream());

                            StringBuilder sb = new StringBuilder();
                            sb.append("q");
                            sb.append(maxUsers);
                            sb.append("q");
                            Integer quan = quantity.get(quantity.size()-1);
                            sb.append(quan);
                            sb.append("q");

                            quantity.remove(quantity.size()-1);

                            System.out.println("Number:" + quan);

                            response.writeUTF(sb.toString());
                            response.flush();
                        } catch (IOException e) {
                            System.out.println("Can't send Quantity of users");
                        }

                        socket.close();
                    }
                    else {
                        List<Socket> sockets = GameServer.getClients();
                        try {
                            for (Socket socket : sockets) {
                                DataOutputStream response = null;
                                try {
                                    response = new DataOutputStream(socket.getOutputStream());
                                    response.writeUTF(string);
                                    response.flush();
                                } catch (IOException e) {
                                    //System.out.println("User has been left");
                                }
                            }
                        } catch (ConcurrentModificationException e){

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
