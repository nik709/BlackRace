package server;

/**
 * Created by Никита on 12.03.2017.
 */
public interface ServerConstants {

    Integer PORT_NUMBER = Integer.valueOf(666);
    String SERVER_ADDRESS = String.valueOf("localhost");
    String SERVER_NAME = String.valueOf("Game Server");

    String FAIL_MESSAGE = String.valueOf("Server has failed");
    String STARTED_MESSAGE = String.valueOf("Server has started on the port ");

    String CONNECTED_MESSAGE = String.valueOf("New client connected");
    String CLIENT_NUMBER_MESSAGE = String.valueOf("Client number: ");
    String FAIL_CONNECTED_MESSAGE = String.valueOf("New client can't connect to the server");

    String STARTED_WORKER_MESSAGE = String.valueOf("Client started the worker");

    String SUCCESSFULL_CONNECTION_TO_DB = String.valueOf("Connection to database COMPLETED");
    String FAILED_CONNECTION_TO_DB = String.valueOf("Connection to database FAILED");



}
