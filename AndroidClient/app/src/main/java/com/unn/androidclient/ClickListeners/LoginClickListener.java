package com.unn.androidclient.ClickListeners;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

import com.unn.androidclient.ClientData.Client;
import com.unn.androidclient.ConnectionListeners.LoginListener;
import com.unn.androidclient.MainMenu;
import com.unn.androidclient.R;
import com.unn.androidclient.Tasks.ConnectionTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

/**
 * Created by Никита on 14.05.2017.
 */

public class LoginClickListener implements View.OnClickListener {
    private Socket socket;
    private Client client;
    private String name;
    private String password;
    private FragmentManager fragmentManager;

    public LoginClickListener(Socket socket, Client client, String name, String password, FragmentManager fragmentManager){
        this.socket = socket;
        this.client = client;
        this.name = name;
        this.password = password;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {
        ConnectionTask connectionTask = new ConnectionTask();
        try {
            socket = connectionTask.execute(client).get();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            LoginListener.sendRequest(out, name, password);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            boolean isAccess = LoginListener.getResponse(in);

            if (isAccess){
                MainMenu mainMenu = new MainMenu();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, mainMenu);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
