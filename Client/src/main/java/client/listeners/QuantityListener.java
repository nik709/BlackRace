package client.listeners;

import client.ClientConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Никита on 22.04.2017.
 */
public class QuantityListener {

    public static HashMap<Integer, Integer> testMap;

    public static HashMap<Integer, Integer> sendRequest(){
        Integer quantity = null;
        Integer number = null;

        Socket socket = null;

        try {
            socket = new Socket(ClientConstants.SERVER_ADDRESS, ClientConstants.PORT_NUMBER);

            DataOutputStream request = new DataOutputStream(socket.getOutputStream());
            StringBuilder sb = new StringBuilder();
            sb.append("uq");

            request.writeUTF(sb.toString());
            request.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DataInputStream response = new DataInputStream(socket.getInputStream());

            String string = response.readUTF();
            if (string.startsWith("q")){
                StringTokenizer st = new StringTokenizer(string);
                if (st.hasMoreTokens())
                    quantity = Integer.valueOf(st.nextToken("q"));
                if (st.hasMoreTokens())
                    number = Integer.valueOf(st.nextToken("q"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(quantity, number);

        testMap = map;

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void printMap(){
        Iterator ik = testMap.keySet().iterator();
        while (ik.hasNext())
            System.out.println(" Key: " + ik.next());

        Iterator iv = testMap.values().iterator();
        while (iv.hasNext())
            System.out.println(" Value: " + iv.next());
    }

}