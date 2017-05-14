package com.unn.androidclient;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.unn.androidclient.ClickListeners.LoginClickListener;
import com.unn.androidclient.ClientData.Client;

import java.net.Socket;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    View view;
    Button login;
    Client client = new Client();
    Socket socket;
    boolean isAccess = false;
    MainMenu mainMenu;
    EditText name;
    EditText password;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        init();
        login.setOnClickListener(new LoginClickListener(socket, client,
                name.getText().toString(), password.getText().toString(),
                getFragmentManager()));
        return view;
    }

    private void init(){

        login = (Button) view.findViewById(R.id.loginB1);
        name = (EditText) view.findViewById(R.id.editName);
        password = (EditText) view.findViewById(R.id.editPass);
    }
}
