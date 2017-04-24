package client.messager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Никита on 09.04.2017.
 */
public class Data {

    private static List<String> data = new ArrayList<>();
    private static String name;

    synchronized public static void addData(Integer index, String s){
        if (data.isEmpty()){
            data.add(String.valueOf(70));
            data.add(String.valueOf(248));
            data.add(String.valueOf(426));
            data.add(String.valueOf(604));
        }
        data.set(index, s);
    }

    public static String getData(Integer index){
        if (data.size()==0)
            return null;

        return data.get(index);
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Data.name = name;
    }
}
