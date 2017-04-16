package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Никита on 16.04.2017.
 */
public class SocketGarbage extends Thread {

    @Override
    public void run(){
        while(true){
            ArrayList<Socket> clients = GameServer.getClients();
            Iterator<Socket> i = clients.iterator();
            while (i.hasNext()){
                if (i.next().isClosed()){
                    i.remove();
                    System.out.println("Socket has been removed");
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
