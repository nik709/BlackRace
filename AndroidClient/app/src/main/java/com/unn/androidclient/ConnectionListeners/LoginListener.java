package com.unn.androidclient.ConnectionListeners;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Никита on 14.05.2017.
 */

public class LoginListener {

    public static void sendRequest(DataOutputStream out, String name, String password) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("??");
        sb.append(name);
        sb.append("??");
        sb.append(password);
        out.writeUTF(sb.toString());
        out.flush();
    }

    public static boolean getResponse(DataInputStream in) throws IOException {
        String respStr = in.readUTF();
        boolean response = Boolean.valueOf(respStr);
        return response;
    }

}
