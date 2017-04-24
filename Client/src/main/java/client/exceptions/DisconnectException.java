package client.exceptions;

import java.io.IOException;

/**
 * Created by Никита on 23.04.2017.
 */
public class DisconnectException extends Exception {

    public DisconnectException(){
        super();
    }

    public DisconnectException(String message){
        super(message);
    }
}
