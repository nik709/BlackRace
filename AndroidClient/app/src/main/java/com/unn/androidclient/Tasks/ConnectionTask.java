package com.unn.androidclient.Tasks;

import android.os.AsyncTask;

import com.unn.androidclient.ClientData.Client;

import java.net.Socket;

/**
 * Created by Никита on 14.05.2017.
 */

public class ConnectionTask extends AsyncTask<Client, Integer, Socket> {
    @Override
    protected void onPostExecute(Socket s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Socket doInBackground(Client... params) {
        params[0].run();
        return params[0].getSocket();
    }
}
