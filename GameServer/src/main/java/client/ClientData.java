package client;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.metadata.IIOMetadataNode;

/**
 * Created by Никита on 22.03.2017.
 */
public class ClientData {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private IIOMetadataNode data = new IIOMetadataNode();

    public IIOMetadataNode getData() {
        return data;
    }

    public ClientData(Integer clientNumber, Double currentX, Boolean isAlive){
        data.setAttribute("Client Number", clientNumber.toString());
        data.setAttribute("X", currentX.toString());
        data.setAttribute("isAlive", isAlive.toString());
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();

        result.append(data.getAttribute("Client Number"));
        result.append(" ");
        result.append(data.getAttribute("X"));
        result.append(" ");
        result.append(data.getAttribute("isAlive"));
        result.append(" ");

        return result.toString();
    }
}
