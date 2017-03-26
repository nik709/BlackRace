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

    public ClientData(Integer clientNumber, Double currentX, Double currentY){

        data.setAttribute("Client Number", clientNumber.toString());
        data.setAttribute("X", currentX.toString());
        data.setAttribute("Y", currentY.toString());
    }
}
