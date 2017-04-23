package server.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ServerConstants;

import java.net.URL;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String fxmlFile = "/fxml/ServerForm.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        primaryStage.setTitle(ServerConstants.SERVER_NAME);
        primaryStage.setScene(new Scene(root, 589, 400));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(Launcher.class);
    }
}
