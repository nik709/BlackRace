package com.unn.androidclient;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unn.androidclient.ClientData.Client;
import com.unn.androidclient.Tasks.ConnectionTask;

import java.net.Socket;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    View view;
    Button login;
    Client client = new Client();
    Socket socket;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        login = (Button) view.findViewById(R.id.loginB1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionTask connectionTask = new ConnectionTask();
                try {
                    socket = connectionTask.execute(client).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
